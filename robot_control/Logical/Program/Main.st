
PROGRAM _INIT
	draw := 5;
	this.start := TRUE;
	this.topSpeed 		:= T#0ms; 	// This parameter will slow the topspeed of all axis.
	this.acceleration	:= 1;		// This parameter will smoothen the acceleration of all axis.
	
END_PROGRAM

PROGRAM _CYCLIC
	(**********************************************************************************************)
	(************************************** Startup sequence **************************************)
	(**********************************************************************************************)
	IF this.start AND gNettime <> 0 THEN
		CASE this.startCase OF
			0: // First find M3 and M2 home switch
				this.M3.backward 	:= TRUE;
				this.M2.backward 	:= TRUE;
				
				IF this.btn3.pressed THEN
					this.M1.backward:= TRUE;
					this.startCase 	:= 10;
				END_IF;
				
			10: // Waiting for M1 and M2 to be homed
				IF this.btn1.pressed THEN
					this.M1.counter	:= 0;
				END_IF;
				
				IF this.btn2.pressed THEN
					this.M2.counter := 0;
				END_IF;
				
				IF this.btn1.pressed AND this.btn2.pressed THEN
					this.startCase := 15;
				END_IF;
				
			15:// going to origo (0,0)
				IF this.M1.counter < 4000 THEN
					this.M1.forward := TRUE;
				ELSE
					this.M1.forward := FALSE;
				END_IF;
				
				IF this.M2.counter < 275 THEN
					this.M2.forward := TRUE;
				ELSE
					this.M2.forward := FALSE;
				END_IF;
				
				IF this.M1.counter >= 4000 AND this.M2.counter >= 275 THEN
					this.startCase := 20;
				END_IF;
				
			20: // make sure the pencil have reached origo with M3
				IF this.btn4.pressed THEN
					this.startCase := 25;
				ELSE
					this.M3.forward := TRUE;
				END_IF;
				
			25: // Set all counter values to 0
				this.M1.counter := 0;
				this.M2.counter := 0;
				this.M3.counter := 0;
				this.startCase := 30;
				
			30: // Lift the pencil from the paper again
				IF this.M3.counter > -500 THEN
					this.M3.backward := TRUE;
				ELSE
					this.M3.backward := FALSE;
					this.startCase := 35;
				END_IF;
				
			35: // Ready to start drawing
				this.start 		:= FALSE;
				this.startCase 	:= FALSE;
				this.isReady 	:= TRUE;
						
		END_CASE;
	END_IF
	
	(**********************************************************************************************)
	(*********************************** Coordinate translation ***********************************)
	(**********************************************************************************************)
	// Read and translate the coordinates from java server
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
				IF this.coord.z = 0 THEN // Black
					this.M3.goToCounter := 110;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 1 THEN
					this.M3.goToCounter := 75;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 2 THEN
					this.M3.goToCounter := 30;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 3 THEN
					this.M3.goToCounter := -65;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 4 THEN
					this.M3.goToCounter := -75;
					this.M3.goTo 		:= TRUE;
				ELSIF this.coord.z = 5 THEN // White
					this.M3.goToCounter := -200;
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
	
	(**********************************************************************************************)
	(*********************************** Pencil sharpener logic ***********************************)
	(**********************************************************************************************)
	// Controls the acceleration when pencil sharpening
	IF EDGEPOS(pSharp) AND this.acceleration < 2 THEN
		this.acceleration 			:= this.acceleration + 2;
		this.accelerationChangePS 	:= TRUE;
	ELSIF EDGENEG(pSharp) AND this.accelerationChangePS THEN
		this.acceleration			:= this.acceleration - 2;
		this.accelerationChangePS	:= FALSE;
	END_IF
	
	// Calculate when to sharp the pencil
	// No wear on the pencil when draw value is 5
	// Approx. 175000 ms if the pencil draw value is 4
	// Approx. 164000 ms if the pencil draw value is 3 
	// Approx. 150000 ms if the pencil draw value is 2 
	// Approx. 130000 ms if the pencil draw value is 1
	// Approx. 100000 ms if the pencil draw value is 0
	IF this.coord.x > 0 AND this.coord.y > 0 AND this.coord.z > 0 THEN
		IF draw = 4 THEN
			dull_counter := dull_counter + 1;
		ELSIF draw = 3 THEN
			dull_counter := dull_counter + 1.06;
		ELSIF draw = 2 THEN
			dull_counter := dull_counter + 1.16;
		ELSIF draw = 1 THEN
			dull_counter := dull_counter + 1.35;
		ELSIF draw = 0 THEN
			dull_counter := dull_counter + 1.75;
		END_IF
	END_IF
	
	IF (dull_counter > 175000) THEN
		pSharp := TRUE;
		dull_counter := 0;
		this.startPS := TRUE;
	END_IF;
	
	IF this.startPS THEN // When the dull counter runs out, pencil sharpener case starts
		CASE this.startPSCase OF
			0: // Store goTo values
				this.M1.goToCounter := this.M1.counter;
				this.M3.goToCounter := this.M3.counter;
				this.m3TempPS		:= this.M3.counter;
				this.startPSCase := 5;
				
			5: // lift the pencil from the drawing plane, and go to an height that fits the pencil sharpener
				this.M3.backward := TRUE;
				
				IF this.M3.counter < -200 AND this.M1.counter > -2500 THEN
					this.M1.backward := TRUE;
				ELSE
					this.M1.backward := FALSE;
				END_IF
				
				IF this.M3.counter = -3200 THEN
					this.M3.backward := FALSE;
					this.startPSCase := 10;
				END_IF
				
			10: // Start moving towards the pencil sharpener, and wait for switch above pencil sharpener to be activated
				this.M1.backward := TRUE;
				IF this.btn1.pressed THEN
					this.M1.backward := FALSE;
					this.startPSCase := 15;
				END_IF
				
			15: // start pencil sharpener, and go into the pencil sharpener
				this.pencilSharp := TRUE;
				this.M3.goToCounter := 110;
				this.M3.goTo := TRUE;
				
				IF EDGEPOS(this.btn4.pressed)THEN
					this.M3.counter	:= 0;
				END_IF
				
				IF this.btn4.pressed THEN
					sharp_counter := sharp_counter +1;
				END_IF
				
				IF sharp_counter > 500 THEN // go deeper
					sharp_counter := 0;
					this.startPSCase := 16;
				END_IF;
				
			16: // start going deeper
				this.M3.goToCounter := 130;
				this.M3.goTo := TRUE;
				sharp_counter := sharp_counter +1;
				
				IF sharp_counter > 500 THEN // go deeper
					sharp_counter := 0;
					this.startPSCase := 17;
				END_IF;
				
			17: // start going deeper
				this.M3.goToCounter := 145;
				this.M3.goTo := TRUE;
				sharp_counter := sharp_counter +1;
				
				IF sharp_counter > 3000 THEN // retreat
					sharp_counter := 0;
					this.startPSCase := 20;
				END_IF;
				
			20: // move out of the pencil sharpener
				this.M3.backward := TRUE;
				
				IF this.M3.counter = -1950 THEN
					this.M3.backward := FALSE;
					this.startPSCase := 25;
					this.pencilSharp := FALSE;
				END_IF
				
			25: // move back to position
				this.M1.goTo := TRUE;
				
				IF this.M1.counter > - 2800 THEN
					this.M3.goToCounter := 800;
					this.M3.goTo 		:= TRUE;
					this.startPSCase 	:= 27;
				END_IF
				
			27: // Calibrate axis 3 on the drawining plane
				IF this.M1.goTo = FALSE AND this.M3.goTo = FALSE THEN
					this.M3.forward := TRUE;
				END_IF
				
				IF this.btn4.pressed THEN
					this.M3.counter 	:= 0;
					this.M3.forward		:= FALSE;
					this.startPSCase 	:= 30;
				END_IF
				
			30: // go to the correct heigt
				IF this.M1.goTo = FALSE THEN
					this.M3.goToCounter := this.m3TempPS;
					this.M3.goTo := TRUE;
					this.startPSCase := 35;
				END_IF
				
			35: // Continues from where it left
				this.startPS := FALSE;
				this.startPSCase := 0;
				pSharp := FALSE;
				
		END_CASE
	END_IF
	
	(**********************************************************************************************)
	(****************************************** Subcasses *****************************************)
	(**********************************************************************************************)
	M1_speedCase;
	M2_speedCase;
	M3_speedCase;
	
END_PROGRAM