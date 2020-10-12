
PROGRAM _INIT

END_PROGRAM


PROGRAM _CYCLIC
	CASE stOpcMethod OF
		OPC_START_OPERATE: // start operate
			UaSrv_MethodOperate_0.Action  := UaMoa_CheckIsCalled; 
			UaSrv_MethodOperate_0.MethodName := 'Multiply';
			UaSrv_MethodOperate_0.Execute := TRUE;
			stOpcMethod := OPC_METHOD_CALLED;
		OPC_METHOD_CALLED: // check if method called
			UaSrv_MethodOperate_0();
			IF UaSrv_MethodOperate_0.Busy THEN
				UaSrv_MethodOperate_0.Execute := FALSE;
			ELSE
				IF UaSrv_MethodOperate_0.Done THEN
					errorID := 0;
					IF UaSrv_MethodOperate_0.IsCalled THEN
						stOpcMethod := OPC_METHOD_EXECUTE; // method is called
					END_IF
					UaSrv_MethodOperate_0.Execute := TRUE;
				END_IF
				IF UaSrv_MethodOperate_0.Error THEN
					errorID:= UaSrv_MethodOperate_0.ErrorID;
					stOpcMethod := OPC_ERROR;
				END_IF
			END_IF
		OPC_METHOD_EXECUTE: // execute method code
			result := ma * mb;
			stOpcMethod := OPC_FINISH_OPERATE;
		OPC_FINISH_OPERATE: // finish operate 
			UaSrv_MethodOperate_0.Action  := UaMoa_Finished;
			UaSrv_MethodOperate_0.MethodName := 'Multiply';
			UaSrv_MethodOperate_0.Execute := TRUE;
			stOpcMethod := OPC_FINISH_METHOD;
		OPC_FINISH_METHOD: // finish method 
			UaSrv_MethodOperate_0();
			IF UaSrv_MethodOperate_0.Busy THEN
				UaSrv_MethodOperate_0.Execute := FALSE;
			ELSE
				IF UaSrv_MethodOperate_0.Done THEN
					errorID:= 0;
					stOpcMethod := OPC_START_OPERATE; // method is finished, wait again for call
				END_IF
				IF UaSrv_MethodOperate_0.Error THEN
					errorID:= UaSrv_MethodOperate_0.ErrorID;
					stOpcMethod := OPC_ERROR;
				END_IF
			END_IF
	END_CASE;
END_PROGRAM


PROGRAM _EXIT

END_PROGRAM

