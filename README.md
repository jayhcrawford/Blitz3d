# Blitz3D

I am taking a video game programming class. Here are some of my experiments in Blitz3D


## Animation Flow
Blitz Basic handles animation by playing select frames from a 3D file format which also includes animation information. 

We need a file of an animated mesh

### The Problem
Parts of the mesh are being dropped in the current pipeline, but it mainly works

### The Solution
So far, the best route I've found is to export animated .B3D from Blender using GreenXenith's fork of Joric's io_scene_b3d. Blitz3D doesn't recognize the animation yet. So, export that B3D from Fragmotion.

At the time of writing this, the animation is mainly working. Mesh from the neck is being dropped by Blitz3D.

So thats:
Animations from Mixamo > Blender > GreenXenith fork of B3D export > Fragmotion export as B3D > import with Blitz Basic > Compile with Dones Blitz3D Plugin for VS Code (Compile with F5)  

So if you want a pathway to having animation happening in Blitz3D, you could start there.

