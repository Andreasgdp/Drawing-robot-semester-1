
TYPE
	m_typ : 	STRUCT 
		endSwitch : BOOL;
		freq : BOOL;
		backward : BOOL;
		forward : BOOL;
		direction : BOOL;
		counter : DINT;
		goTo : BOOL;
		goToCounter : DINT;
		speedCaseTON : TON;
		speedCase : USINT;
		overRideCase : USINT; (*This case is used if you want to go further than the endswitch*)
	END_STRUCT;
	this_typ : 	STRUCT 
		M2 : m_typ;
		M3 : m_typ;
		M1 : m_typ;
		topSpeed : TIME; (*T#0ms = 1 bit every 1 ms, T#5ms = 1 bit every 5 ms*)
		acceleration : USINT; (*0 = instant topspeed, 10 = slowest acceleration*)
		btn2 : btn_typ;
		isReady : BOOL;
		btn4 : btn_typ;
		btn3 : btn_typ;
		btn1 : btn_typ;
		startCase : USINT;
		start : BOOL;
		startPSCase : USINT;
		startPS : BOOL;
		pencilSharp : BOOL;
		greyScale : USINT;
		print : BOOL; (*If print is equal to 1 then it should be printing*)
		coord : coord_typ;
	END_STRUCT;
	btn_typ : 	STRUCT 
		pressed : BOOL;
	END_STRUCT;
END_TYPE
