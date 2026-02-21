# Blitz3D

I am taking a video game programming class. Here are some of my experiments in Blitz3D


## Animation Flow

### The Problem
None of the Blender plugins that I found worked the way that I hoped. Animation wasn't supported. If you find a better way, let me know.

### The Solution
So far, the best route I've found is to export .FBX from Blender, including the animation. Then, Fragmotion can handle importing the .FBX and exporting the .B3D

At the time of writing this, I still haven't made certain the process fully works--the animation works, but the mesh export is broken (probably due to my not implementing Blender transforms properly).

So thats:
Animations from Mixamo > Blender > Fragmotion > Dones Blitz3D Plugin for VS Code (Compile with F5)  
