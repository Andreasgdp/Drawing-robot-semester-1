
TYPE
	m_typ : 	STRUCT 
		endSwitch : BOOL;
		freq : BOOL;
		backward : BOOL;
		forward : BOOL;
		direction : BOOL;
		counter : DINT;
	END_STRUCT;
	this_typ : 	STRUCT 
		M2 : m_typ;
		M3 : m_typ;
		M1 : m_typ;
		btn2 : btn_typ;
		isReady : BOOL;
		btn4 : btn_typ;
		btn3 : btn_typ;
		btn1 : btn_typ;
		startCase : USINT;
		start : BOOL;
	END_STRUCT;
	btn_typ : 	STRUCT 
		pressed : BOOL;
	END_STRUCT;
END_TYPE
