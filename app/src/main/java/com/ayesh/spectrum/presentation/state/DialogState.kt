package com.xiteb.argo.presentation.state


sealed class DialogState{
   object Info : DialogState()
   object Error : DialogState()
   object General : DialogState()
   object Success : DialogState()
}
