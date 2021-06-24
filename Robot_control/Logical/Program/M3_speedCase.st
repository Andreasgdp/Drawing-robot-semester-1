
ACTION M3_speedCase:
	
	CASE this.M3.speedCase OF
		0: // highest speed
			this.M3.speedCaseTON.PT := this.topSpeed;
			
		1:
			this.M3.speedCaseTON.PT := this.topSpeed + T#1ms;
		
		2:
			this.M3.speedCaseTON.PT := this.topSpeed + T#2ms;
			
		3:
			this.M3.speedCaseTON.PT := this.topSpeed + T#3ms;
		
		4:
			this.M3.speedCaseTON.PT := this.topSpeed + T#4ms;
		
		5:
			this.M3.speedCaseTON.PT := this.topSpeed + T#5ms;
			
		6:
			this.M3.speedCaseTON.PT := this.topSpeed + T#6ms;
			
		7:
			this.M3.speedCaseTON.PT := this.topSpeed + T#7ms;
			
		8:
			this.M3.speedCaseTON.PT := this.topSpeed + T#8ms;
			
		9:
			this.M3.speedCaseTON.PT := this.topSpeed + T#9ms;
			
		10:
			this.M3.speedCaseTON.PT := this.topSpeed + T#10ms;
		
	END_CASE
	
	IF this.M3.forward OR this.M3.backward THEN // Set the frequence
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
	
	IF this.M3.forward THEN // Logic for acceleration
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
		this.M3.speedCase := this.acceleration;
		
	END_IF

	// Logic for goto
	IF this.M3.goTo AND (this.M3.goToCounter <> this.M3.counter) THEN
		IF this.M3.counter < this.M3.goToCounter THEN
			this.M3.forward := TRUE;
			this.M3.backward := FALSE;
		ELSIF this.M3.counter > this.M3.goToCounter THEN
			this.M3.backward := TRUE;
			this.M3.forward := FALSE;
		END_IF
	ELSIF this.M3.goTo AND (this.M3.goToCounter = this.M3.counter) THEN
		this.M3.backward := FALSE;
		this.M3.forward := FALSE;
		this.M3.goTo := FALSE;
	END_IF
	
	// Calibration of m3 position
	IF EDGEPOS(this.btn4.pressed) THEN
		IF NOT(this.M3.goTo) THEN
			this.M3.counter	:= 0; 
			this.M3.forward := FALSE;
		END_IF
	END_IF;
	
	
	CASE this.M3.overRideCase OF
		0:
			IF this.M3.goTo AND this.M3.goToCounter > 0 AND this.btn4.pressed THEN
				this.M3.counter	:= 0;
				this.M3.overRideCase := 5;
			END_IF
					
		5:
			IF this.M3.goToCounter = this.M3.counter AND NOT(this.M3.goTo) AND this.M3.counter < 0 THEN
				this.M3.overRideCase := 0;
			END_IF	
	END_CASE
				
	// Safety feature
	IF this.btn3.pressed THEN
		this.M3.backward := FALSE;
	END_IF;
	
	this.M3.speedCaseTON ();
	
END_ACTION
