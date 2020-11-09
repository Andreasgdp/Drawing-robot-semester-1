	
PROGRAM _INIT
	tcpst := TCPSTATE_SOCKET_OPEN;
	coord_counter := 1;
	x_coord := 0;
	y_coord := 0;
	draw := 0;
	sendData := '';
	receiveData := '';
	goTo := '';
	
	FOR i:= 0 TO LENGT_OF_COORD DO 	// We do not have coordinates with values lower than 0,
		coord[i].x := -1;  			// that is why we set the init values to -1, to recognize
		coord[i].y := -1;			// the empty coordinates
		coord[i].z := -1;
	END_FOR;
	
END_PROGRAM
	
PROGRAM _CYCLIC
	IF (coord[1].x = -1 AND coord[1].y = -1 AND coord[1].z = -1) OR		// This if statement, makes sure that we will not fill the array
		(coord[3].x = -1 AND coord[3].y = -1 AND coord[3].z = -1) THEN 	// and keep getting new coordinates, with nowhere to store them.
	CASE tcpst OF
		TCPSTATE_SOCKET_OPEN :
			TcpOpen1.enable := TRUE;
			TcpOpen1.port := 2050;
			TcpOpen1.options := tcpOPT_REUSEADDR;
			TcpOpen1();
			IF (TcpOpen1.status = ERR_OK) THEN
				tcpst := TCPSTATE_SERVER_CREATE;
			END_IF
			
		TCPSTATE_SERVER_CREATE :
			IF goTo = sendData AND goTo = receiveData AND goTo <> '' THEN
				IF coord_counter = 1 THEN
					x_coord := brsatoi(ADR(goTo));
					goTo := '';
				ELSIF coord_counter = 2 THEN
					y_coord := brsatoi(ADR(goTo));
					goTo := '';
				ELSIF coord_counter = 3 THEN
					draw := brsatoi(ADR(goTo));
					goTo := '';
				END_IF
				sendData := receiveData := '';
				// goTo := '';
				coord_counter := coord_counter + 1;
				IF coord_counter >= 4 AND coord[0].x = -1 AND coord[0].y = -1 AND coord[0].z = -1 THEN
					coord[0].x := x_coord;
					coord[0].y := y_coord;
					coord[0].z := draw;
					coord_counter := 1;
					goTo := 'done';
					elPreben := TRUE;
				END_IF
			END_IF;
			
			TcpServer1.enable := TRUE;
			TcpServer1.ident := TcpOpen1.ident;
			TcpServer1();
			
			IF goTo = 'done' AND (TcpServer1.status = ERR_OK) THEN
				sendData := goTo;
				// goTo := '';
				tcpst := TCPSTATE_SEND;
			ELSIF (TcpServer1.status = ERR_OK) THEN
				tcpst := TCPSTATE_RECEIVE;
			END_IF
	
		TCPSTATE_RECEIVE :			
			TcpReceive1.enable := TRUE;
			TcpReceive1.ident := TcpServer1.identclnt;
			TcpReceive1.pData := ADR(receiveData);
			TcpReceive1.datamax := SIZEOF(receiveData);
			TcpReceive1();
			IF (TcpReceive1.status = ERR_OK) THEN
				tcpst := TCPSTATE_SEND;
				sendData := receiveData;
			END_IF
	
		TCPSTATE_SEND :
			TcpSend1.enable := TRUE;
			TcpSend1.ident := TcpServer1.identclnt;
			TcpSend1.pData := ADR(sendData);
			TcpSend1.datalen := brsstrlen(ADR(sendData));     // We use brsstrlen() to get the length in UDINT datatype
			TcpSend1();
			IF (TcpSend1.status = ERR_OK) THEN
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
			IF (TcpClose1.status = ERR_OK) THEN
				test := FALSE;
				TcpServer1.enable := FALSE;
				TcpServer1();
				tcpst := TCPSTATE_SERVER_CREATE;
			END_IF
			
		END_CASE
	END_IF
	
	// This is the logic for a 'First In First Out' (FIFO) system
	FOR i := 1 TO LENGT_OF_COORD DO
		IF (coord[i].x = -1 AND coord[i].y = -1 AND coord[i].z = -1) AND 					// IF number i coordinates is empty
			(coord[i - 1].x <> -1 AND coord[i - 1].y <> -1 AND coord[i -1].z <> -1) THEN	// AND the number i - 1 has coordinates
			coord[i].x 		:= coord[i - 1].x;												// THEN move the i - 1 coordinates to i coordinates location
			coord[i].y 		:= coord[i - 1].y;
			coord[i].z 		:= coord[i - 1].z;
			coord[i - 1].x	:= -1;
			coord[i - 1].y	:= -1;
			coord[i - 1].z	:= -1;
		END_IF
	END_FOR;
	
END_PROGRAM

	
