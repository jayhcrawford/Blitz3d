# Blitz3D

I am taking a video game programming class. Here are some of my experiments in Blitz3D


## Animation Flow
Blitz Basic handles animation by playing select frames from a 3D file format which also includes animation information. 

We need a file of an animated mesh

### The Problem
Blender produces our mesh/animation.
None of the Blender plugins that I found worked the way that I hoped. Animation wasn't supported. If you find a better way, let me know.

I did try baking the animation multiple different ways, and the B3D export tools available to Blender still didn't handle it as I'd have liked.

### The Solution
So far, the best route I've found is to export .FBX from Blender, including the animation. Then, Fragmotion can handle importing the .FBX and exporting the .B3D

At the time of writing this, I still haven't made certain the process fully works--the animation works, but the mesh export is broken (probably due to my not implementing Blender transforms properly).

So thats:
Animations from Mixamo > Blender > Fragmotion > Dones Blitz3D Plugin for VS Code (Compile with F5)  

So if you want a pathway to having animation happening in Blitz3D, you could start there.

