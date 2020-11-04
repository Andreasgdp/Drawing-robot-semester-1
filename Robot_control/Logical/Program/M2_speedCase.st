
ACTION M2_speedCase:
	
	CASE this.M2.speedCase OF
		0: // highest speed
			this.M2.speedCaseTON.PT := SPEED;
			
		1:
			this.M2.speedCaseTON.PT := SPEED + T#1ms;
		
		2:
			this.M2.speedCaseTON.PT := SPEED + T#2ms;
			
		3:
			this.M2.speedCaseTON.PT := SPEED + T#3ms;
		
		4:
			this.M2.speedCaseTON.PT := SPEED + T#4ms;
		
		5:
			this.M2.speedCaseTON.PT := SPEED + T#5ms;
			
		6:
			this.M2.speedCaseTON.PT := SPEED + T#6ms;
			
		7:
			this.M2.speedCaseTON.PT := SPEED + T#7ms;
			
//		8:
//			this.M2.speedCaseTON.PT := SPEED + T#8ms;
//			
//		9:
//			this.M2.speedCaseTON.PT := SPEED + T#9ms;
//			
//		10:
//			this.M2.speedCaseTON.PT := SPEED + T#10ms;
		
	END_CASE
	
	IF this.M2.forward OR this.M2.backward THEN
		IF this.M2.speedCaseTON.Q THEN
			this.M2.speedCaseTON.IN := FALSE;
				
			IF this.M2.freq THEN
				this.M2.freq 		:= FALSE;
			ELSE
				this.M2.freq		:= TRUE;
			END_IF
				
		ELSE
			this.M2.speedCaseTON.IN := TRUE;
				
		END_IF
	END_IF
	
	IF this.M2.forward THEN
		this.M2.direction := TRUE;
	
		IF EDGEPOS(this.M2.freq) THEN
			this.M2.counter := this.M2.counter + 1;
			
			IF this.M2.speedCase > 0 THEN
				this.M2.speedCase := this.M2.speedCase - 1;
			END_IF
				
		ELSIF EDGEPOS(NOT(this.M2.freq)) THEN
			this.M2.counter := this.M2.counter + 1;
		END_IF;
	ELSIF this.M2.backward THEN
		this.M2.direction := FALSE;
		
		IF EDGEPOS(this.M2.freq) THEN
			this.M2.counter := this.M2.counter - 1;
			
			IF this.M2.speedCase > 0 THEN
				this.M2.speedCase := this.M2.speedCase - 1;
			END_IF
			
		ELSIF EDGEPOS(NOT(this.M2.freq)) THEN
			this.M2.counter := this.M2.counter - 1;
		END_IF;
		
	END_IF
	IF EDGEPOS(NOT(this.M2.backward)) OR EDGEPOS(NOT(this.M2.forward)) THEN
		this.M2.speedCase := 10;
		
	END_IF

	this.M2.speedCaseTON ();
END_ACTION
