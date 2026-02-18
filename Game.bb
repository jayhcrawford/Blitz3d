; =========================
; BASIC 3D SHOOTER - BLITZ3D
; =========================

Graphics3D 800,600,32,2
SetBuffer BackBuffer()

; --- Player & Camera ---
player = CreatePivot()
PositionEntity player, 0,2,0

camera = CreateCamera(player)
PositionEntity camera, 0,0,0
CameraRange camera, 0.1, 1000

; --- Light ---
light = CreateLight()
RotateEntity light, 45,45,0

; --- Ground ---
ground = CreatePlane()
EntityColor ground, 50,150,50
EntityType ground, 2
EntityPickMode ground, 2

; --- Player Settings ---
playerSpeed# = 0.2
mouseSpeed# = 0.2
gravity# = 0.15
playerPitch# = 0
HidePointer()

; --- Enemy Type ---
Type Enemy
    Field entity
End Type

; --- Create Enemies ---
For i = 1 To 10
    e.Enemy = New Enemy
    e\entity = CreateCube()
    PositionEntity e\entity, Rnd(-20,20),1,Rnd(5,40)
    EntityColor e\entity, 200,50,50
    EntityPickMode e\entity, 2
Next

; --- Collision Setup ---
EntityType player, 1
EntityRadius player, 0.4, 1.8
Collisions 1,2,2,2

; --- Main Game Loop ---
While Not KeyHit(1) ; ESC to quit

    ; Mouse Look (yaw on player, pitch on camera)
    mxs# = MouseXSpeed() * mouseSpeed#
    mys# = MouseYSpeed() * mouseSpeed#
    TurnEntity player, 0, -mxs#, 0
    playerPitch# = playerPitch# + mys#
    If playerPitch# > 80 Then playerPitch# = 80
    If playerPitch# < -80 Then playerPitch# = -80
    RotateEntity camera, playerPitch#, 0, 0
    
    ; WASD Movement
    If KeyDown(17) Then MoveEntity player,0,0,playerSpeed# ; W
    If KeyDown(31) Then MoveEntity player,0,0,-playerSpeed# ; S
    If KeyDown(30) Then MoveEntity player,-playerSpeed#,0,0 ; A
    If KeyDown(32) Then MoveEntity player,playerSpeed#,0,0 ; D

    ; Gravity to keep player grounded
    MoveEntity player, 0, -gravity#, 0
    
    ; Shooting
    If MouseHit(1)
        picked = CameraPick(camera, GraphicsWidth()/2, GraphicsHeight()/2)
        If picked <> 0
            For e.Enemy = Each Enemy
                If e\entity = picked
                    FreeEntity e\entity
                    Delete e
                    Exit
                EndIf
            Next
        EndIf
    EndIf
    
    UpdateWorld
    RenderWorld
    
    Text 10,10,"WASD to move"
    Text 10,25,"Mouse to look"
    Text 10,40,"Left Click to shoot"
    Text 10,55,"ESC to quit"
    
    Flip
Wend

End
