
PROGRAM _INIT
	(* Insert code here *)
	this.var1;
	this.var2;
	
END_PROGRAM

PROGRAM _CYCLIC
	(* Insert code here *)
	IF this.M1.forward THEN
		this.M1.direction := TRUE;
		IF this.M1.freq THEN
			this.M1.freq := 0;
		ELSE
			this.M1.freq := 1;
		END_IF;
	END_IF;
	 
END_PROGRAM

PROGRAM _EXIT
	(* Insert code here *)
	 
END_PROGRAM

