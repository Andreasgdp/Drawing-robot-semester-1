
PROGRAM _INIT
	(* Insert code here *)
	this.start := TRUE;
	this.startCase := 20;
	this.isReady := FALSE;
	this.M1.counter := 0;
	this.M2.counter := 0;
	this.M3.counter := 0;
END_PROGRAM

PROGRAM _CYCLIC
	(* Insert code here *)
	IF this.start THEN
		CASE this.startCase OF
			0:
				this.M3.backward := TRUE;
				this.M2.backward := TRUE;
				this.startCase := 5;
			5:
				
				IF this.btn3.pressed THEN
					this.startCase := 10;
				END_IF;
			10:
				IF this.btn1.pressed THEN
					this.M1.backward := FALSE;
				ELSE
					this.M1.backward := TRUE;
				END_IF;
				IF this.btn2.pressed THEN
					this.M2.backward := FALSE;
				END_IF;
				IF this.btn1.pressed AND this.btn2.pressed THEN
					this.startCase := 15;
				END_IF;
			15:// going to origo (0,0)
				IF this.M1.counter < 2500 THEN
					this.M1.forward := TRUE;
				ELSE
					//this.M1.backward := FALSE;
					this.M1.forward := FALSE;
				END_IF;
				
				IF this.M2.counter < 10800 THEN
					this.M2.forward := TRUE;
				ELSE
					//this.M1.backward := FALSE;
					this.M2.forward := FALSE;
				END_IF;
				
				IF this.M1.counter >= 2500 AND this.M2.counter >= 10800 THEN
					this.startCase := 20;
				END_IF;
			20:
				IF this.btn4.pressed THEN
					this.startCase := 25;
				ELSE
					this.M3.forward := TRUE;
				END_IF;
			25:
				this.M1.counter := 0;
				this.M2.counter := 0;
				this.M3.counter := 0;
				this.isReady := TRUE;
				this.startCase := 30;
			30:
				IF this.M3.counter < 500 THEN
					this.M3.backward := TRUE;
				ELSE
					this.M3.backward := FALSE;
					this.startCase := 35;
				END_IF;
			35:
				this.start := FALSE;
						
		END_CASE;
 
	END_IF;
	
	IF this.M1.forward THEN
		this.M1.direction := TRUE;
		IF this.M1.freq THEN
			this.M1.freq := 0;
			this.M1.counter := this.M1.counter + 1;
		ELSE
			this.M1.freq := 1;
			this.M1.counter := this.M1.counter + 1;
		END_IF;
	END_IF;
	
	IF this.M1.backward THEN
		this.M1.direction := FALSE;
		IF this.M1.freq THEN
			this.M1.freq := 0;
			IF this.isReady THEN
				this.M1.counter := this.M1.counter - 1;
			END_IF;
			
		ELSE
			this.M1.freq := 1;
			IF this.isReady THEN
				this.M1.counter := this.M1.counter - 1;
			END_IF;
			
		END_IF;
	END_IF;
	
	IF this.M2.forward THEN
		this.M2.direction := TRUE;
		IF this.M2.freq THEN
			this.M2.freq := 0;
			this.M2.counter := this.M2.counter + 1;
		ELSE
			this.M2.freq := 1;
			this.M2.counter := this.M2.counter + 1;
		END_IF;
	END_IF;
	
	IF this.M2.backward THEN
		this.M2.direction := FALSE;
		IF this.M2.freq THEN
			this.M2.freq := 0;
			IF this.isReady THEN
				this.M2.counter := this.M2.counter - 1;
			END_IF;
		ELSE
			this.M2.freq := 1;
			IF this.isReady THEN
				this.M2.counter := this.M2.counter - 1;
			END_IF;
		END_IF;
	END_IF;
	
	IF this.M3.forward THEN
		this.M3.direction := TRUE;
		IF this.M3.freq THEN
			this.M3.freq := 0;
			IF this.isReady THEN
				this.M3.counter := this.M3.counter - 1;
			END_IF;
		ELSE
			this.M3.freq := 1;
			IF this.isReady THEN
				this.M3.counter := this.M3.counter - 1;
			END_IF;
		END_IF;
	END_IF;
	
	IF this.M3.backward THEN
		this.M3.direction := FALSE;
		IF this.M3.freq THEN
			this.M3.freq := 0;
			IF this.isReady THEN
				this.M3.counter := this.M3.counter + 1;
			END_IF;
		ELSE
			this.M3.freq := 1;
			IF this.isReady THEN
				this.M3.counter := this.M3.counter + 1;
			END_IF;
		END_IF;
	END_IF;
	
	IF this.btn1.pressed THEN
	  this.M1.backward := FALSE;
	END_IF;
	
	IF this.btn2.pressed THEN
		this.M2.backward := FALSE;
	END_IF;
	
	IF this.btn4.pressed THEN
		this.M3.forward := FALSE;
	END_IF;
	
	IF this.btn3.pressed THEN
		this.M3.backward := FALSE;
	END_IF;
END_PROGRAM

PROGRAM _EXIT
	(* Insert code here *)
	 
END_PROGRAM

