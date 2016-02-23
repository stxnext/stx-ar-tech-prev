using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using Vuforia;

[RequireComponent(typeof(Camera))]
public class CameraFlip : MonoBehaviour
{
    // Public members can be changed in the Inspector
    public bool flipFrontCamera = false;
    public bool flipBackCamera = true;
    public bool flipVertical = true;
    public bool flipHorizontal = true;

    private Transform mBackgroundPlane;
    private bool mFlipped = false;
    private bool mCameraReady = false;

    void Start()
    {
        VuforiaBehaviour.Instance.RegisterVuforiaStartedCallback(OnVuforiaStarted);
        VuforiaBehaviour.Instance.RegisterOnPauseCallback(OnVuforiaPause);
    }

    void OnVuforiaStarted()
    {
        Debug.Log("Vuforia has started");
        mBackgroundPlane = transform.GetComponentInChildren<Transform>();
        mCameraReady = true;
    }

    void OnVuforiaPause(bool paused)
    {
        if (!paused) {
            mFlipped = false;
        }
    }

    void OnPreCull()
    {
        // Skip if Vuforia has not started yet
        if (!mCameraReady)
            return;

        bool isFrontCam = (CameraDevice.Instance.GetCameraDirection() == CameraDevice.CameraDirection.CAMERA_FRONT);
        bool isBackCam = (CameraDevice.Instance.GetCameraDirection() == CameraDevice.CameraDirection.CAMERA_BACK) ||
        (CameraDevice.Instance.GetCameraDirection() == CameraDevice.CameraDirection.CAMERA_DEFAULT);

        if ((isFrontCam && flipFrontCamera) ||
        (isBackCam && flipBackCamera)) {
            if (!mFlipped) {
                FlipCameraProjectionMatrix();
                mFlipped = true;
            }
        }
    }

    private void FlipCameraProjectionMatrix()
    {
        Camera cam = this.GetComponent<Camera>();
        Vector3 flipScale = new Vector3(flipHorizontal ? -1 : 1, flipVertical ? -1 : 1, 1);
        Matrix4x4 projMat = cam.projectionMatrix * Matrix4x4.Scale(flipScale);
        Debug.Log("Flipping camera projection matrix...");
        cam.projectionMatrix = projMat;
    }

    void LateUpdate()
    {
        // Skip if Vuforia has not started yet
        if (!mCameraReady)
            return;

        Vector3 planeScaleAbs = new Vector3(Mathf.Abs(mBackgroundPlane.localScale.x),
                Mathf.Abs(mBackgroundPlane.localScale.y),
                Mathf.Abs(mBackgroundPlane.localScale.z));

        bool isFrontCam = (CameraDevice.Instance.GetCameraDirection() == CameraDevice.CameraDirection.CAMERA_FRONT);
        bool isBackCam = (CameraDevice.Instance.GetCameraDirection() == CameraDevice.CameraDirection.CAMERA_BACK) ||
        (CameraDevice.Instance.GetCameraDirection() == CameraDevice.CameraDirection.CAMERA_DEFAULT);

        if ((isFrontCam && flipFrontCamera) ||
        (isBackCam && flipBackCamera)) {
            // flip background plane
            mBackgroundPlane.localScale = new Vector3(planeScaleAbs.x * (flipHorizontal ? -1 : 1),
                    planeScaleAbs.y * (flipVertical ? -1 : 1),
                    planeScaleAbs.z);
        } else {
            // do NOT flip background plane
            mBackgroundPlane.localScale = new Vector3(planeScaleAbs.x, planeScaleAbs.y, planeScaleAbs.z);
        }
    }

    void OnPreRender()
    {
        if (flipVertical != flipHorizontal) {
            GL.SetRevertBackfacing(true);
        }
    }

    // Set it to false again because we don't want to affect all other cameras.
    void OnPostRender()
    {
        if (flipVertical != flipHorizontal) {
            GL.SetRevertBackfacing(false);
        }
    }
}