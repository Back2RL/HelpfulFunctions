import Vector.FVector;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// write your code here
		final float dt = 0.333f;
		// world pitch rotation?
		float pitchRotation = 0;
		// ?
		float mouseRate = 1.0f;
		boolean cont = false;
		do {
cont = false;
			FVector velocity = new FVector(1, 0, 0);
			FVector velDir = velocity.getNormalized();

			FVector upVel = new FVector(0, 0, 1);
			FVector upVelDir = upVel.getNormalized();

			FVector sideVel = FVector.cross(upVelDir, velDir);

			int screenCenterX = 960;
			int screenCenterY = 540;

			// dir from camera into world space
			FVector mouseDir = new FVector(1, 0, 0).normalize();
			float temp = -(upVelDir.dot(mouseDir) / upVelDir.getLengthSquared());

			float mousePosX = 0;
			float mousePosY = 0;

			FVector newVel = new FVector(upVelDir.getX() * temp + mouseDir.getX(),
					upVelDir.getY() * temp + mouseDir.getY(),
					upVelDir.getZ() * temp + mouseDir.getZ());
			newVel.normalize();

			float AIR_UPDOWNRATE_MAX = 0.5f;

			float angle = (AIR_UPDOWNRATE_MAX * mousePosY) / screenCenterY;
			if (angle > AIR_UPDOWNRATE_MAX) angle = AIR_UPDOWNRATE_MAX;
			else if (angle < -AIR_UPDOWNRATE_MAX) angle = -AIR_UPDOWNRATE_MAX;

			if (Math.abs(screenCenterY - mousePosY) > 3.0f * (2 * screenCenterY) / 600.0f) {
				float fSpeedPenalty = 0.5f;
				pitchRotation += fSpeedPenalty * dt * angle * 0.1f;
				if (angle > 0 && pitchRotation > angle)
					pitchRotation = angle;
				else if (angle < 0 && pitchRotation < angle)
					pitchRotation = angle;
				// pitch rotationaxis
				//D3DXMatrixRotationAxis(&matTemp, sideVel, mouseRate * CurrentAngle * dt);
				// pitch rotation
				//D3DXVec3TransformCoord(&velDir, &velDir, &matTemp);
			}
			System.out.println(pitchRotation);

			Scanner in = new Scanner(System.in);
			while(!in.hasNextBoolean()){
				in.next();
			}
			cont= in.nextBoolean();



		} while (cont);

		}


}

