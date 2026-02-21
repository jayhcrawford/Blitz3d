Graphics3D 1024,768,32,2
SetBuffer BackBuffer()

Global menuActive = True

Global camera
Global light
Global cube

camera = CreateCamera()
PositionEntity camera,0,5,-10

light = CreateLight()

cube = CreateCube()
EntityColor cube,0,150,255

Global btnWidth = 200
Global btnHeight = 60
Global btnX = (GraphicsWidth()/2) - (btnWidth/2)
Global btnY = (GraphicsHeight()/2) + 40

While Not KeyHit(1)

    If menuActive = True Then
        DrawMenu()
    Else
        UpdateGame()
    EndIf

    Flip
Wend

End


Function DrawMenu()

    Cls

    Color 20,20,20
    Rect 0,0,GraphicsWidth(),GraphicsHeight(),1

    Color 60,60,60
    Rect GraphicsWidth()/2-250, GraphicsHeight()/2-150, 500,300,1

    title$ = "MY BLITZ3D GAME"
    Color 255,255,255
    Text GraphicsWidth()/2 - StringWidth(title$)/2, GraphicsHeight()/2-100, title$

    mx = MouseX()
    my = MouseY()

    hover = False
    If mx > btnX And mx < btnX+btnWidth And my > btnY And my < btnY+btnHeight Then hover = True

    If hover = True Then
        Color 0,200,0
    Else
        Color 0,120,0
    EndIf

    Rect btnX, btnY, btnWidth, btnHeight,1

    btnText$ = "GO"
    Color 255,255,255
    Text btnX + btnWidth/2 - StringWidth(btnText$)/2, btnY + btnHeight/2 - StringHeight(btnText$)/2, btnText$

    If hover = True And MouseHit(1) Then menuActive = False

End Function


Function UpdateGame()

    Cls
    TurnEntity cube,0,1,0
    RenderWorld()

End Function
