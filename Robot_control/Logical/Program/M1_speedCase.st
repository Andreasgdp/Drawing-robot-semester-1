
ACTION M1_speedCase:
	
	CASE this.M1.speedCase OF
		0: // highest speed
			this.M1.speedCaseTON.PT := this.topSpeed;
			
		1:
			this.M1.speedCaseTON.PT := this.topSpeed + T#1ms;
		
		2:
			this.M1.speedCaseTON.PT := this.topSpeed + T#2ms;
			
		3:
			this.M1.speedCaseTON.PT := this.topSpeed + T#3ms;
		
		4:
			this.M1.speedCaseTON.PT := this.topSpeed + T#4ms;
		
		5:
			this.M1.speedCaseTON.PT := this.topSpeed + T#5ms;
			
		6:
			this.M1.speedCaseTON.PT := this.topSpeed + T#6ms;
			
		7:
			this.M1.speedCaseTON.PT := this.topSpeed + T#7ms;
			
		8:
			this.M1.speedCaseTON.PT := this.topSpeed + T#8ms;
			
		9:
			this.M1.speedCaseTON.PT := this.topSpeed + T#9ms;
			
		10:
			this.M1.speedCaseTON.PT := this.topSpeed + T#10ms;
		
	END_CASE
	
	IF this.M1.forward OR this.M1.backward THEN // Set the frequence
		IF this.M1.speedCaseTON.Q THEN
			this.M1.speedCaseTON.IN := FALSE;
				
			IF this.M1.freq THEN
				this.M1.freq 		:= FALSE;
			ELSE
				this.M1.freq		:= TRUE;
			END_IF	
		ELSE
			this.M1.speedCaseTON.IN := TRUE;	
		END_IF
	END_IF
	
	IF this.M1.forward THEN // Logic for acceleration
		this.M1.direction := TRUE;
	
		IF EDGEPOS(this.M1.freq) THEN
			this.M1.counter := this.M1.counter + 1;
			
			IF this.M1.speedCase > 0 THEN
				this.M1.speedCase := this.M1.speedCase - 1;
			END_IF
				
		ELSIF EDGEPOS(NOT(this.M1.freq)) THEN
			this.M1.counter := this.M1.counter + 1;
		END_IF;
	ELSIF this.M1.backward THEN
		this.M1.direction := FALSE;
		
		IF EDGEPOS(this.M1.freq) THEN
			this.M1.counter := this.M1.counter - 1;
			
			IF this.M1.speedCase > 0 THEN
				this.M1.speedCase := this.M1.speedCase - 1;
			END_IF
			
		ELSIF EDGEPOS(NOT(this.M1.freq)) THEN
			this.M1.counter := this.M1.counter - 1;
		END_IF;
		
	END_IF
	IF EDGEPOS(NOT(this.M1.backward)) OR EDGEPOS(NOT(this.M1.forward)) THEN
		this.M1.speedCase := this.acceleration;
		
	END_IF

	// Goto Logic
	IF this.M1.goTo AND (this.M1.goToCounter <> this.M1.counter) THEN
		IF this.M1.counter < this.M1.goToCounter THEN
			this.M1.forward := TRUE;
			this.M1.backward := FALSE;
		ELSIF this.M1.counter > this.M1.goToCounter THEN
			this.M1.backward := TRUE;
			this.M1.forward := FALSE;
		END_IF
	ELSIF this.M1.goTo AND (this.M1.goToCounter = this.M1.counter) THEN
		this.M1.backward := FALSE;
		this.M1.forward := FALSE;
		this.M1.goTo := FALSE;
	END_IF
	
	// safety feature
	IF this.btn1.pressed THEN
		this.M1.backward := FALSE;
	END_IF;
	
	this.M1.speedCaseTON ();
	
END_ACTION
