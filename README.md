# Blitz3D

I am taking a video game programming class. Here are some of my experiments in Blitz3D

## Working Demos

**Third Person Template.bb** here: 
https://youtu.be/q3g6t6aD_wA


## Animation Flow
Blitz Basic handles animation by playing select frames from a 3D file format which also includes animation information. 

We need a file of an animated mesh

### The Problem
Blitz3D is an old game engine... as *"a legacy engine (early 2000s)"*
It has certain expectations:
- One armature
- One mesh
- Simple skin weights
- Clean transforms (scale = 1, rotation = 0)

Standard Blender → B3D exports often result in:

- Detached mesh parts
- Offset bones
- Broken animation
- Scale issues (0.01 armature scale problems)

B3D exporters don't necessarily handle this in a way that makes Blender > Blitz3D direct.

### The Solution

The GreenXenith Blender plugin fork properly exports the animations in a way that Fragmotion can then read. The plugin unfortunately doesn't export straight into Blitz3D.

There is a zip of the  plugin in the blender_plugin dir of this repo

Or find it here: https://github.com/GreenXenith/io_scene_b3d

----

### Required Blender Setup
#### 1. Clean Transforms

**Ensure:**
 - Armature scale = 1
 - Mesh scale = 1

**Apply transforms:**
```
Ctrl + A → Apply All Transforms
```
Do this before final export.

To do this correctly, I had to individually import the .fbx animation files from Mixamo, change their scale from 0.01 to 1, and then transfer the animation keyframes onto one armature.


#### 2. Join Meshes

Blitz3D expects a single skinned mesh.

If your model has separate parts (head, body, clothing), join them:
```
Shift Select meshes → Ctrl + J
```
After joining, confirm:

One mesh object, One armature modifier, Proper vertex groups


#### 3. Keep the Rig Simple

Avoid:
- Constraints
- IK solvers
- Drivers
- Non-deform bones

Bake animation to pure pose keyframes if needed. You may need to look up that baking process, or it might work.

Blitz3D prefers simple, fully baked animation data.

#### 4. Export settings

##### A. Blender 
Export as a .B3D using GreenXenith's fork of io_scene_b3d

Uncheck all **Limit To** (your scene should have one character mesh, and one armature, delete any lights/cameras/extras)

for **Object Types** check Mesh and Armature

for **Mesh** check UVs, Materials, Normals

##### B. Fragmotion

Export as a .B3D

Uncheck heirarchical mesh, uncheck exclude normals, for me add root node is not an option. You may need to explore.

## Working Export Pipeline

```
Mixamo
   ↓
Blender (cleanup, join mesh, apply transforms)
   ↓
GreenXenith B3D Exporter (Blender fork)
   ↓
Fragmotion
   ↓
Export as B3D from Fragmotion
   ↓
LoadAnimMesh() in Blitz3D
   ↓
Compile with VS Code (Dones Blitz3D plugin, F5)
```

## Why Fragmotion Is Needed

The GreenXenith fork correctly exports animation data in a format Fragmotion understands.

### However:

The Blender exporter does not consistently produce a .b3d file that Blitz3D reads correctly.

**Fragmotion acts as a compatibility bridge.**

### Reliable path:

Blender → B3D → Fragmotion → B3D → Blitz3D

