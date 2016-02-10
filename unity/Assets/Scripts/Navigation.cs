using UnityEngine;
using System.Collections;

public class Navigation : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		if (Input.GetKeyDown(KeyCode.Escape)) {
            AndroidJavaClass javaClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            AndroidJavaObject javaObject = javaClass.GetStatic<AndroidJavaObject>("currentActivity");
            javaObject.Call("onUnityBackPressed");
        }
	}
}
