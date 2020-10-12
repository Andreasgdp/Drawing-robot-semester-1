
PROGRAM _INIT
	(* Insert code here *)
	this.start := TRUE;
	this.startCase := 0;
	this.isReady := FALSE;
	this.M1.counter := 0;
	this.M1.goToCounter := -1;
	this.M2.counter := 0;
	this.M2.goToCounter := -1;
	this.M3.counter := 0;
	this.startPS := FALSE;
	this.startPSCase := 0;
	goTo := '';
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
				IF this.M1.counter < 5000 THEN
					this.M1.forward := TRUE;
				ELSE
					//this.M1.backward := FALSE;
					this.M1.forward := FALSE;
				END_IF;
				
				IF this.M2.counter < 150 THEN
					this.M2.forward := TRUE;
				ELSE
					//this.M1.backward := FALSE;
					this.M2.forward := FALSE;
				END_IF;
				
				IF this.M1.counter >= 5000 AND this.M2.counter >= 150 THEN
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
	END_IF
	
	IF goTo = 'ready' THEN
		IF (draw = TRUE) AND (case_draw <> 10) THEN
			case_draw := 0;
		ELSIF draw = FALSE THEN
			case_draw := 10;
		END_IF;
		CASE case_draw OF
			0:
				IF this.btn4.pressed THEN
					this.M3.forward := FALSE;
					case_draw := 10;
				ELSE
					this.M3.forward := TRUE;
				END_IF;
			10:
				this.M1.goToCounter := x_coord * 20;
				this.M2.goToCounter := y_coord * 20;
				this.M1.goTo := TRUE;
				this.M2.goTo := TRUE;
		END_CASE;

	END_IF;
	
	//ny kode
	IF this.M1.goTo AND (this.M1.goToCounter <> this.M1.counter) THEN
		IF this.M1.counter < this.M1.goToCounter THEN
			this.M1.forward := TRUE;
		ELSIF this.M1.counter > this.M1.goToCounter THEN
			this.M1.backward := TRUE;
	
		END_IF
	ELSIF this.M1.goTo AND (this.M1.goToCounter = this.M1.counter) THEN
		this.M1.backward := FALSE;
		this.M1.forward := FALSE;
		this.M1.goTo := FALSE;
		
	END_IF

	
	IF this.M2.goTo AND (this.M2.goToCounter <> this.M2.counter) THEN
		IF this.M2.counter < this.M2.goToCounter THEN
			this.M2.forward := TRUE;
		ELSIF this.M2.counter > this.M2.goToCounter THEN
			this.M2.backward := TRUE;
			
		END_IF
	ELSIF this.M2.goTo AND (this.M2.goToCounter = this.M2.counter) THEN
		this.M2.backward := FALSE;
		this.M2.forward := FALSE;
		this.M2.goTo := FALSE;	
		
	END_IF

	
	IF this.M3.goTo AND (this.M3.goToCounter <> this.M3.counter) THEN
		IF this.M3.counter < this.M3.goToCounter THEN
			this.M3.backward := TRUE;
		ELSIF this.M3.counter > this.M2.goToCounter THEN
			this.M3.forward := TRUE;
	
		END_IF
	ELSIF this.M3.goTo AND (this.M3.goToCounter = this.M3.counter) THEN
		this.M3.backward := FALSE;
		this.M3.forward := FALSE;
		this.M3.goTo := FALSE;
		
	END_IF

	IF (this.M1.goTo = FALSE) AND (this.M2.goTo = FALSE) AND (this.M2.goToCounter = this.M2.counter) AND (this.M1.goToCounter = this.M1.counter) AND (goTo = 'ready') THEN
		IF this.M3.counter < 500 THEN
			this.M3.goToCounter := 510;
			this.M3.goTo := TRUE;
		ELSE
			goTo := 'done';
			this.M1.goToCounter := -1;
			this.M2.goToCounter := -1;
			case_draw := 0;
		END_IF;
	END_IF;
	
	IF this.startPS THEN
		CASE this.startPSCase OF
			0:
				this.M1.goToCounter := this.M1.counter;
				this.M3.goToCounter := this.M3.counter;
				this.M3.backward := TRUE;
				this.startPSCase := 5;
			5:
				IF this.M3.counter = 3500 THEN
					this.M3.backward := FALSE;
					this.startPSCase := 10;
				END_IF
			10:
				this.M1.backward := TRUE;
				IF this.btn1.pressed THEN
					this.M1.backward := FALSE;
					this.startPSCase := 15;
				END_IF
			15:
				this.pencilSharp := TRUE;
				this.M3.forward := TRUE;
				IF this.btn4.pressed THEN
					IF sharp_counter < 1 THEN
						this.M3.forward := FALSE;
					END_IF;
					sharp_counter := sharp_counter + 1;
					sharp_counter2 := sharp_counter2 + 1;
					
					IF sharp_counter = 600 THEN
						this.M3.forward := FALSE;
						this.M3.backward := TRUE;
						sharp_counter := 0;
						this.startPSCase := 20;
					ELSIF sharp_counter2 = 200 THEN
						this.M3.forward := NOT this.M3.forward;
						sharp_counter2 := 0;
					END_IF;
				END_IF
			20:
				IF this.M3.counter = 3500 THEN
					this.M3.backward := FALSE;
					this.startPSCase := 25;
					this.pencilSharp := FALSE;
				END_IF
			25:
				this.M1.goTo := TRUE;
				this.startPSCase := 30;
				
			30:
				IF this.M1.goTo = FALSE THEN
					this.M3.goTo := TRUE;
					this.startPSCase := 35;
				END_IF
				
			35:
				this.startPS := FALSE;
				this.startPSCase := 0;
				
		END_CASE
	END_IF
				
			
				
				
	//slut ny kode			

	
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

