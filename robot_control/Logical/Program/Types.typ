
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
		var1 : USINT;
		M2 : m_typ;
		M3 : m_typ;
		M1 : m_typ;
		var2 : USINT;
	END_STRUCT;
END_TYPE
