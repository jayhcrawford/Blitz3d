Graphics3D 1024,768,32,2
SetBuffer BackBuffer()

; --- Collision Types ---
Const TYPE_PLAYER = 1
Const TYPE_WORLD  = 2

; --- Camera ---
camera = CreateCamera()
CameraRange camera, 0.1, 1000

; --- Player Pivot (for movement + collision) ---
player = CreatePivot()
PositionEntity player,0,1,0
EntityRadius player,0.5,1
EntityType player, TYPE_PLAYER

; --- Lights ---
light = CreateLight(1)

;light2 = CreateLight()
; MoveEntity light2,5,0,0
; PointEntity light2,player ; make sure light is pointing at ball


; --- Visible Player Mesh ---
;playerMesh = CreateCube() 
Global playerMesh = loadMesh("Baked_Swat_guy.3ds")

Print AnimLength(playerMesh)
;DebugLog CountFrames(playerMesh)
ScaleEntity playerMesh,1,1,1
RotateEntity playerMesh,0,90,0
EntityParent playerMesh, player
EntityColor playerMesh,255,0,0


; --- Other Visible Mesh ---
staticMesh = CreateCube()
ScaleEntity staticMesh,0.5,10,0.5
MoveEntity staticMesh,20,0,0
EntityColor staticMesh,255,0,0

; --- Ground ---
ground = CreatePlane()
ScaleEntity ground,50,1,50
EntityType ground, TYPE_WORLD

; --- Create Checker Texture ---
texSize = 256
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
ScaleTexture checkerTex, 20,20

; --- Collision Setup ---
Collisions TYPE_PLAYER, TYPE_WORLD, 2, 2

; --- Camera Settings ---
camDistance# = 6
camHeight# = 2
camSmooth# = 0.1

; --- Gravity ---
gravity# = 0.02
yVel# = 0
groundY# = 1 ; Height of ground plane center'
Global currentAnim = 0 ; Initialize animation state

If AnimLength(playerMesh) > 0 Then
    Animate playerMesh, 0
    SetAnimTime playerMesh, 47
Else
    RuntimeError "Mesh has no baked animation frames!"
EndIf

; --- Animation Data ---

Const ANIM_IDLE = 0
Const ANIM_WALK = 1
Const ANIM_RUN  = 2

Dim animStart(2)
Dim animEnd(2)
Dim animSpeed#(2)

animStart(ANIM_IDLE) = 46
animEnd(ANIM_IDLE)   = 226
animSpeed(ANIM_IDLE) = 0.1

animStart(ANIM_WALK) = 1
animEnd(ANIM_WALK)   = 41
animSpeed(ANIM_WALK) = 0.2

animStart(ANIM_RUN) = 1
animEnd(ANIM_RUN)   = 41
animSpeed(ANIM_RUN) = 0.35


Function SetAnimation(mesh, anim)

    If anim = currentAnim Then Return

    currentAnim = anim

    Animate mesh, 1, animSpeed(anim), animStart(anim), animEnd(anim)

End Function

; --- Main Loop ---
While Not KeyHit(1)

    ; --- Movement ---
    isMoving = False
    
    If KeyDown(32) Then TurnEntity player,0,-2,0 ; D
    If KeyDown(30) Then TurnEntity player,0,2,0  ; A
    
    If KeyDown(17)
        MoveEntity player,0.1,0,0 ; W
        isMoving = True
    EndIf
    
    If KeyDown(31)
        MoveEntity player,-0.05,0,0 ; S
        isMoving = True
    EndIf

    ; --- Animation Control ---
    If isMoving
        SetAnimation(playerMesh, ANIM_WALK)
    Else
        SetAnimation(playerMesh,ANIM_IDLE)
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
