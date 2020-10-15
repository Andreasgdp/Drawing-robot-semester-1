
PROGRAM _INIT
	tcpst := TCPSTATE_SOCKET_OPEN;
	coord_counter := 1;
	x_coord := 0;
	y_coord := 0;
	draw := 0;
	sendData := '';
	receiveData := '';
	goTo := '';
END_PROGRAM

PROGRAM _CYCLIC
	CASE tcpst OF
		TCPSTATE_SOCKET_OPEN :
			TcpOpen1.enable := TRUE;
			TcpOpen1.port := 12345;
			TcpOpen1.options := tcpOPT_REUSEADDR;
			TcpOpen1();
			IF (TcpOpen1.status = ERR_OK) OR (TcpOpen1.status = 32600) THEN
				tcpst := TCPSTATE_SERVER_CREATE;
			END_IF
			
		TCPSTATE_SERVER_CREATE :
			IF goTo = sendData AND goTo = receiveData AND goTo <> '' THEN
				IF coord_counter = 1 THEN
					x_coord := brsatoi(ADR(goTo));
				ELSIF coord_counter = 2 THEN
					y_coord := brsatoi(ADR(goTo));
				ELSIF coord_counter = 3 THEN
					draw := brsatoi(ADR(goTo));
				END_IF
				sendData := receiveData := '';
				// goTo := '';
				coord_counter := coord_counter + 1;
				IF coord_counter >= 4 THEN
					coord_counter := 1;
					goTo := 'ready';
					elPreben := TRUE;
				END_IF
				
			END_IF;
			IF goTo = 'done' THEN
				sendData := goTo;
				// goTo := '';
				tcpst := TCPSTATE_SEND;
			END_IF;
			TcpServer1.enable := TRUE;
			TcpServer1.ident := TcpOpen1.ident;
			TcpServer1();
			IF (TcpServer1.status = ERR_OK) OR (TcpServer1.status = 32600) THEN
				tcpst := TCPSTATE_RECEIVE;
			END_IF

		TCPSTATE_RECEIVE :
			IF goTo = 'done' THEN
				tcpst := TCPSTATE_SERVER_CREATE;
			ELSIF goTo = '' AND elPreben = TRUE THEN
				F_It_TON.PT 	:= T#5000ms;
				F_It_TON.IN 	:= TRUE;
				
				IF F_It_TON.Q THEN
					goTo 		:= 'done';
					elPreben 	:= FALSE;
					F_It_TON.IN := TRUE;
				END_IF
			END_IF
			
			TcpReceive1.enable := TRUE;
			TcpReceive1.ident := TcpServer1.identclnt;
			TcpReceive1.pData := ADR(receiveData);
			TcpReceive1.datamax := SIZEOF(receiveData);
			TcpReceive1();
			IF (TcpReceive1.status = ERR_OK) OR (TcpReceive1.status = 32600) THEN
				tcpst := TCPSTATE_SEND;
				sendData := receiveData;
			END_IF

		TCPSTATE_SEND :
			TcpSend1.enable := TRUE;
			TcpSend1.ident := TcpServer1.identclnt;
			TcpSend1.pData := ADR(sendData);
			TcpSend1.datalen := brsstrlen(ADR(sendData));     // We use brsstrlen() to get the length in UDINT datatype
			TcpSend1();
			IF (TcpSend1.status = ERR_OK) OR (TcpSend1.status = 32600) THEN
				tcpst := TCPSTATE_CLOSE;
			END_IF

		TCPSTATE_CLOSE :
		goTo := sendData;
			IF sendData = 'done' THEN
				sendData := '';
			END_IF;
			TcpClose1.enable := TRUE;
			TcpClose1.ident := TcpServer1.identclnt;
			TcpClose1();
			IF (TcpClose1.status = ERR_OK) OR (TcpClose1.status = 32600) THEN
				TcpServer1.enable := FALSE;
				TcpServer1();
				tcpst := TCPSTATE_SERVER_CREATE;
			END_IF
			
	END_CASE
	
	IF tcpst <> TCPSTATE_RECEIVE THEN
		F_It_TON.IN := FALSE;
	END_IF
	
	
	F_It_TON();
	
END_PROGRAM

PROGRAM _EXIT
	TcpClose1.enable := TRUE;
	TcpClose1.ident := TcpServer1.identclnt;
	TcpClose1();

	TcpClose2.enable := TRUE;
	TcpClose2.ident := TcpOpen1.ident;
	TcpClose2();
END_PROGRAM

