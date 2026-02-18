; ===============================
; CLEAN BASIC 3D SHOOTER
; ===============================

Graphics3D 800,600,32,2
SetBuffer BackBuffer()

; ---------- CONSTANTS ----------
Const TYPE_PLAYER = 1
Const TYPE_WORLD  = 2
Const TYPE_ENEMY  = 3

; ---------- PLAYER ----------
player = CreatePivot()
PositionEntity player, 0,3,0
EntityType player, TYPE_PLAYER
EntityRadius player, 0.4,1.0

camera = CreateCamera(player)
PositionEntity camera, 0,1.6,0
CameraRange camera,0.1,1000

; ---------- LIGHT ----------
light = CreateLight()
RotateEntity light,45,45,0

; ---------- GROUND ----------
ground = CreatePlane()
EntityColor ground,50,150,50
EntityType ground,TYPE_WORLD
EntityPickMode ground,2

; ---------- COLLISIONS ----------
Collisions TYPE_PLAYER, TYPE_WORLD, 2,2
Collisions TYPE_PLAYER, TYPE_ENEMY, 2,2

; ---------- PLAYER SETTINGS ----------
speed# = 0.25
gravity# = 0.1
mouseSpeed# = 0.2
pitch# = 0
HidePointer()

; ---------- ENEMY TYPE ----------
Type Enemy
    Field entity
End Type

; ---------- CREATE ENEMIES ----------
For i = 1 To 10
    e.Enemy = New Enemy
    e\entity = CreateCube()
    PositionEntity e\entity,Rnd(-20,20),1,Rnd(10,40)
    EntityColor e\entity,200,50,50
    EntityType e\entity,TYPE_ENEMY
    EntityPickMode e\entity,2
Next

; ---------- MAIN LOOP ----------
While Not KeyHit(1)

    ; ---- Mouse Look ----
    mx# = MouseXSpeed()*mouseSpeed#
    my# = MouseYSpeed()*mouseSpeed#
    
    TurnEntity player,0,-mx#,0
    
    pitch# = pitch# + my#
    If pitch# > 80 Then pitch# = 80
    If pitch# < -80 Then pitch# = -80
    RotateEntity camera,pitch#,0,0

    ; ---- Movement ----
    If KeyDown(17) Then MoveEntity player,0,0,speed#
    If KeyDown(31) Then MoveEntity player,0,0,-speed#
    If KeyDown(30) Then MoveEntity player,-speed#,0,0
    If KeyDown(32) Then MoveEntity player,speed#,0,0

    ; ---- Gravity ----
    MoveEntity player,0,-gravity#,0

    ; ---- Shooting ----
    If MouseHit(1)
    
        ; draw quick flash line
        picked = CameraPick(camera,GraphicsWidth()/2,GraphicsHeight()/2)
        
        If picked <> 0
            
            For e.Enemy = Each Enemy
                If e\entity = picked
                    
                    ; visual feedback
                    EntityColor e\entity,255,255,0
                    FreeEntity e\entity
                    Delete e
                    Exit
                    
                EndIf
            Next
            
        EndIf
    EndIf

    UpdateWorld
    RenderWorld
    
    ; ---- Crosshair ----
    Color 255,255,255
    Line GraphicsWidth()/2-5,GraphicsHeight()/2,GraphicsWidth()/2+5,GraphicsHeight()/2
    Line GraphicsWidth()/2,GraphicsHeight()/2-5,GraphicsWidth()/2,GraphicsHeight()/2+5

    Text 10,10,"WASD Move"
    Text 10,25,"Mouse Look"
    Text 10,40,"Click Shoot"
    Text 10,55,"ESC Quit"

    Flip

Wend

End
