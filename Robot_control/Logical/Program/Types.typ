
TYPE
	m_typ : 	STRUCT  (*Types used for all motors*)
		endSwitch : BOOL;
		freq : BOOL; (*Output frequency for the steppermotor*)
		backward : BOOL; (*Backward movement towards the switch when true*)
		forward : BOOL; (*Forward movement when true*)
		direction : BOOL; (*Output direction for the steppermotor*)
		counter : DINT; (*Actual position*)
		goTo : BOOL; (*When true, the axis will goTo the goToCounter value*)
		goToCounter : DINT; (*The postion the axis should go to*)
		speedCaseTON : TON; (*Used to control acceleration*)
		speedCase : USINT; (*Used to control acceleration*)
		overRideCase : USINT; (*This case is used if you want to go further than the endswitch*)
	END_STRUCT;
	this_typ : 	STRUCT  (*Local structure type*)
		M1 : m_typ;
		M2 : m_typ;
		M3 : m_typ;
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
		accelerationChangePS : BOOL; (*If the acceleration have been changed when pencil sharpening, then this bool will be true*)
		m3TempPS : DINT;
		pencilSharp : BOOL;
		greyScale : USINT;
		print : BOOL; (*If print is equal to 1 then it should be printing*)
		coord : coord_typ;
	END_STRUCT;
	btn_typ : 	STRUCT 
		pressed : BOOL;
	END_STRUCT;
END_TYPE
