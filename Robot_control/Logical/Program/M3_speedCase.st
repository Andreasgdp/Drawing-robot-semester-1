
ACTION M3_speedCase:
	
	CASE this.M3.speedCase OF
		0: // highest speed
			this.M3.speedCaseTON.PT := T#0ms;
			
		1:
			this.M3.speedCaseTON.PT := T#1ms;
		
		2:
			this.M3.speedCaseTON.PT := T#2ms;
			
		3:
			this.M3.speedCaseTON.PT := T#3ms;
		
		4:
			this.M3.speedCaseTON.PT := T#4ms;
		
		5:
			this.M3.speedCaseTON.PT := T#5ms;
			
		6:
			this.M3.speedCaseTON.PT := T#6ms;
			
		7:
			this.M3.speedCaseTON.PT := T#7ms;
			
		8:
			this.M3.speedCaseTON.PT := T#8ms;
			
		9:
			this.M3.speedCaseTON.PT := T#9ms;
			
		10:
			this.M3.speedCaseTON.PT := T#10ms;
		
	END_CASE
	
	IF this.M3.forward OR this.M3.backward THEN
		IF this.M3.speedCaseTON.Q THEN
			this.M3.speedCaseTON.IN := FALSE;
				
			IF this.M3.freq THEN
				this.M3.freq 		:= FALSE;
			ELSE
				this.M3.freq		:= TRUE;
			END_IF
				
		ELSE
			this.M3.speedCaseTON.IN := TRUE;
				
		END_IF
	END_IF
	
	IF this.M3.forward THEN
		this.M3.direction := TRUE;
	
		IF EDGEPOS(this.M3.freq) THEN
			this.M3.counter := this.M3.counter + 1;
			
			IF this.M3.speedCase > 0 THEN
				this.M3.speedCase := this.M3.speedCase - 1;
			END_IF
				
		ELSIF EDGEPOS(NOT(this.M3.freq)) THEN
			this.M3.counter := this.M3.counter + 1;
		END_IF;
	ELSIF this.M3.backward THEN
		this.M3.direction := FALSE;
		
		IF EDGEPOS(this.M3.freq) THEN
			this.M3.counter := this.M3.counter - 1;
			
			IF this.M3.speedCase > 0 THEN
				this.M3.speedCase := this.M3.speedCase - 1;
			END_IF
			
		ELSIF EDGEPOS(NOT(this.M3.freq)) THEN
			this.M3.counter := this.M3.counter - 1;
		END_IF;
		
	END_IF
	IF EDGEPOS(NOT(this.M3.backward)) OR EDGEPOS(NOT(this.M3.forward)) THEN
		this.M3.speedCase := 3;
		
	END_IF

	this.M3.speedCaseTON ();
END_ACTION
