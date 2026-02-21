; 3ps - A simple 3rd person character controller example in Blitz3D

Graphics3D 1024,768,32,2
SetBuffer BackBuffer()

; --- Collision Types ---
Const TYPE_PLAYER = 1
Const TYPE_WORLD  = 2

; --- Camera ---
camera = CreateCamera()
CameraRange camera, 0.1, 1000

light = CreateLight()

; --- Player Pivot (for movement + collision) ---
player = CreatePivot()
PositionEntity player,0,1,0
EntityRadius player,0.5,1
EntityType player, TYPE_PLAYER


;light2 = CreateLight()
; MoveEntity light2,5,0,0
; PointEntity light2,player ; make sure light is pointing at ball


 ; --- Visible Player Mesh ---
;playerMesh = CreateCube()
Global playerMesh = LoadAnimMesh("Frag Motion Test 14 Exp.b3d")
RotateEntity playerMesh, 0, 90, 0

Print AnimLength(playerMesh)

Global animWalkSeq = ExtractAnimSeq(playerMesh,190,231)
Global animBWWalkSeq = ExtractAnimSeq(playerMesh,232,273)
Global animIdleSeq = ExtractAnimSeq(playerMesh,1,189)
Global currentAnimSeq = animIdleSeq
Animate playerMesh,1,0.1,currentAnimSeq

; --- Visible Player Mesh ---
;playerMesh = CreateCube()
ScaleEntity playerMesh,2.5,2.5,2.5
EntityParent playerMesh, player
EntityColor playerMesh,255,0,0

; --- Ground ---
ground = CreatePlane()
ScaleEntity ground,50,1,50
EntityType ground, TYPE_WORLD

; --- Create Checker Texture ---
texSize = 512
checkerTex = CreateTexture(texSize, texSize)

SetBuffer TextureBuffer(checkerTex)

Color 255,255,255
Rect 0,0,texSize/2,texSize/2,True
Rect texSize/2,texSize/2,texSize/2,texSize/2,True

Color 0,0,0
Rect texSize/2,0,texSize/2,texSize/2,True
Rect 0,texSize/2,texSize/2,texSize/2,True

SetBuffer BackBuffer()

EntityTexture ground, checkerTex
ScaleTexture checkerTex, 1,1

; --- Collision Setup ---
Collisions TYPE_PLAYER, TYPE_WORLD, 2, 2

; --- Camera Settings ---
camDistance# = 6
camHeight# = 2
camSmooth# = 0.1

; --- Gravity ---
gravity# = 0.02
yVel# = 0
groundY# = 1 ; Height of ground plane center

; --- Main Loop ---
While Not KeyHit(1)

  movingForward = False
  movingBackward = False

    ; --- Movement ---
    If KeyDown(32) Then TurnEntity player,0,-2,0 ; D
    If KeyDown(30) Then TurnEntity player,0,2,0  ; A
    
  If KeyDown(17)
    MoveEntity player,0.1,0,0 ; W
    movingForward = True
  EndIf
  If KeyDown(31)
    MoveEntity player,-0.05,0,0 ; S
    movingBackward = True
  EndIf

  If movingForward = True
    If currentAnimSeq <> animWalkSeq
      currentAnimSeq = animWalkSeq
      Animate playerMesh,1,1,currentAnimSeq,5
    EndIf
  ElseIf movingBackward = True
    If currentAnimSeq <> animBWWalkSeq
      currentAnimSeq = animBWWalkSeq
      Animate playerMesh,1,1,currentAnimSeq,5
    EndIf
  Else
    If currentAnimSeq <> animIdleSeq
      currentAnimSeq = animIdleSeq
      Animate playerMesh,1,0.1,currentAnimSeq,5
    EndIf
  EndIf

    ; --- Gravity ---
    yVel# = yVel# - gravity#
    MoveEntity player,0,yVel#,0
    
    If EntityY(player) < groundY# + 0.5
        yVel# = 0
        PositionEntity player, EntityX(player), groundY# + 0.5, EntityZ(player)
    EndIf

    ; --- Camera Follow ---
    targetX# = EntityX(player) - Cos(EntityYaw(player)) * camDistance#
    targetZ# = EntityZ(player) - Sin(EntityYaw(player)) * camDistance#
    targetY# = EntityY(player) + camHeight#
    
    camX# = EntityX(camera)
    camY# = EntityY(camera)
    camZ# = EntityZ(camera)
    
		PositionEntity camera, camX# + (targetX# - camX#) * camSmooth#, camY# + (targetY# - camY#) * camSmooth#, camZ# + (targetZ# - camZ#) * camSmooth#
    
    PointEntity camera, player

    UpdateWorld
    RenderWorld
    Flip

Wend

End
