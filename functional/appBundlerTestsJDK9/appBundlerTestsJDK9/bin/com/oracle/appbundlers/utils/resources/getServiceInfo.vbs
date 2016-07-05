Set objWMIService = GetObject("winmgmts:{impersonationLevel=impersonate}!\\.\root\cimv2")
Set colRunningServices = objWMIService.ExecQuery("SELECT __PROPERTY__ FROM Win32_Service WHERE Name = '__SERVICE_NAME__'")    

If colRunningServices.Count = 0 Then
	Return
End If
For Each objService in colRunningServices          
     WScript.Echo "Description::- " & objService.Description  & VbCrLf 
Next