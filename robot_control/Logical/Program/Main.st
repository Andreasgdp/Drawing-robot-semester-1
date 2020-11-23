
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
	this.topSpeed 		:= T#0ms; 	// This parameter will slow the topspeed of all axis.
	this.acceleration	:= 1;		// This parameter will set the acceleration of all axis.
END_PROGRAM

PROGRAM _CYCLIC
	(* Insert code here *)
	IF this.start AND gNettime <> 0 THEN
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
					this.M1.counter	:= 0;
				ELSE
					this.M1.backward := TRUE;
				END_IF;
				IF this.btn2.pressed THEN
					this.M2.backward := FALSE;
					this.M2.counter := 0;
				END_IF;
				IF this.btn1.pressed AND this.btn2.pressed THEN
					this.startCase := 15;
				END_IF;
			15:// going to origo (0,0)
				IF this.M1.counter < 4000 THEN
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
				
				IF this.M1.counter >= 4000 AND this.M2.counter >= 150 THEN
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
				IF this.M3.counter > -500 THEN
					this.M3.backward := TRUE;
				ELSE
					this.M3.backward := FALSE;
					this.startCase := 35;
				END_IF;
			35:
				this.start 		:= FALSE;
				this.startCase 	:= FALSE;
						
		END_CASE;
	END_IF
	
	(*this.greyScale := DINT_TO_USINT(draw);*)
	
	IF NOT pSharp THEN
		CASE case_draw OF
			0: // start the case
				IF coord[LENGT_OF_COORD].x <> -1 AND coord[LENGT_OF_COORD].y <> -1 AND coord[LENGT_OF_COORD].z <> -1 THEN
					this.coord.x := coord[LENGT_OF_COORD].x;
					this.coord.y := coord[LENGT_OF_COORD].y;
					this.coord.z := coord[LENGT_OF_COORD].z;
					coord[LENGT_OF_COORD].x := -1;
					coord[LENGT_OF_COORD].y := -1;
					coord[LENGT_OF_COORD].z := -1;
					case_draw := 5;
				END_IF
				
				
			5: // which height should we goto?
				IF this.coord.z = 0 THEN
					this.M3.goToCounter := 100;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 1 THEN
					this.M3.goToCounter := 0;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 2 THEN
					this.M3.goToCounter := -59;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 3 THEN
					this.M3.goToCounter := -70;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 4 THEN
					this.M3.goToCounter := -100;
					this.M3.goTo 		:= TRUE;
				
				
				ELSIF this.coord.z = 5 THEN
					this.M3.goToCounter := -120;
					this.M3.goTo 		:= TRUE;
				END_IF
				
				IF this.M3.counter = this.M3.goToCounter THEN
					case_draw := 10;
				END_IF
					
			10: // Goto all the coordinates, and then go back to state 0 to calculate next coordinate
				this.M1.goToCounter := this.coord.x * 40;
				this.M2.goToCounter := this.coord.y * 40;
				this.M1.goTo := TRUE;
				this.M2.goTo := TRUE;
				IF this.M3.counter <> this.M3.goToCounter THEN
					this.M3.goTo := TRUE;
				END_IF			
			
				IF (this.M1.goToCounter = this.M1.counter AND this.M2.goToCounter = this.M2.counter AND this.M3.goToCounter = this.M3.counter) THEN
					case_draw := 0;
				END_IF
			
		END_CASE;

	END_IF;
	
	//ny kode
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

	
	IF this.M2.goTo AND (this.M2.goToCounter <> this.M2.counter) THEN
		IF this.M2.counter < this.M2.goToCounter THEN
			this.M2.forward := TRUE;
			this.M2.backward := FALSE;
		ELSIF this.M2.counter > this.M2.goToCounter THEN
			this.M2.backward := TRUE;
			this.M2.forward := FALSE;
		END_IF
	ELSIF this.M2.goTo AND (this.M2.goToCounter = this.M2.counter) THEN
		this.M2.backward := FALSE;
		this.M2.forward := FALSE;
		this.M2.goTo := FALSE;	
		
	END_IF

	
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
	
	IF draw > 0 AND draw < 5 AND NOT(pSharp) THEN
		dull_counter := dull_counter + 1;
	END_IF;
	
	IF (dull_counter > 150000) THEN
		pSharp := TRUE;
		dull_counter := 0;
		this.startPS := TRUE;
		
	END_IF;
	
	IF this.startPS THEN
		CASE this.startPSCase OF
			0:
				this.M1.goToCounter := this.M1.counter;
				this.M3.goToCounter := this.M3.counter;
				this.startPSCase := 5;
			5:
				this.M3.backward := TRUE;
				IF this.M3.counter = -3200 THEN
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
				
				this.M3.goToCounter := 75;
				this.M3.goTo := TRUE;
				
				IF this.btn4.pressed THEN
					sharp_counter := sharp_counter +1;
				END_IF
				
				IF sharp_counter > 2000 THEN
					sharp_counter := 0;
					this.M3.backward := TRUE;
					this.startPSCase := 20;
				END_IF;
				
			20:
				this.M3.backward := TRUE;
				
				IF this.M3.counter = -3200 THEN
					this.M3.backward := FALSE;
					this.startPSCase := 25;
					this.pencilSharp := FALSE;
				END_IF
			25:
				this.M1.goTo := TRUE;
				this.startPSCase := 27;
			27:
				IF this.M1.goTo = FALSE THEN
					this.M3.forward := TRUE;
				END_IF
				
				IF this.btn4.pressed THEN
					this.M3.counter 	:= 0;
					this.M3.forward		:= FALSE;
					this.startPSCase 	:= 30;
				END_IF
			30:
				IF this.M1.goTo = FALSE THEN
					this.M3.goTo := TRUE;
					this.startPSCase := 35;
				END_IF
				
			35:
				this.startPS := FALSE;
				this.startPSCase := 0;
				pSharp := FALSE;
				
		END_CASE
	END_IF
				
			
				
				
	//slut ny kode			

	//speedCase
	M1_speedCase;
	M2_speedCase;
	M3_speedCase;
	IF this.btn1.pressed THEN
		this.M1.backward := FALSE;
	END_IF;
	
	IF this.btn2.pressed THEN
		this.M2.backward := FALSE;
	END_IF;
	
	IF EDGEPOS(this.btn4.pressed) AND NOT(pSharp) THEN
		IF NOT(this.M3.goTo) THEN
			this.M3.counter	:= 0; // Emil was here
			this.M3.forward := FALSE;
		END_IF
	END_IF;
	
	IF NOT(pSharp) THEN
		CASE this.M3.overRideCase OF
			0:
				IF this.M3.goTo AND this.M3.goToCounter > 0 AND this.btn4.pressed THEN
					this.M3.counter	:= 0; // Christoffer was here
					this.M3.overRideCase := 5;
				END_IF
					
			5:
				IF this.M3.goToCounter = this.M3.counter AND NOT(this.M3.goTo) AND this.M3.counter < 0 THEN
					this.M3.overRideCase := 0;
				END_IF	
		END_CASE
	END_IF
				
	
	IF this.btn3.pressed THEN
		this.M3.backward := FALSE;
	END_IF;
		

END_PROGRAM

PROGRAM _EXIT
	(* Insert code here *)
	 
END_PROGRAM