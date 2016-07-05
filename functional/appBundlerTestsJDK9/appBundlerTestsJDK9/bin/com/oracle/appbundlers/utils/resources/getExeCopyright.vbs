Set filesys = CreateObject("Scripting.FileSystemObject")
folder = filesys.GetParentFolderName(Wscript.ScriptFullName)

Set objShell = CreateObject("Shell.Application")
Set objFolder = objShell.Namespace(folder)

Set objFolderItem = objFolder.ParseName("__FILE_NAME__")

Dim arrHeaders(256)
For i = 0 to 256
	header = objFolder.GetDetailsOf(objFolder.Items, i)
	If (header = "") Then
		Exit For
	End If
	arrHeaders(i) = header
Next

For i = 0 to UBound(arrHeaders)
	If (lcase(arrHeaders(i)) = "copyright") Then
		Wscript.Echo objFolder.GetDetailsOf(objFolderItem, i)
		Exit For	
	End If
Next
