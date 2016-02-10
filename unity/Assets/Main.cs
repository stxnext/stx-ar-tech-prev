using UnityEngine;
using System.Collections;
using System;

public class Main : MonoBehaviour {

    public GameObject logo;
    public GameObject dinosaur;
    public GameObject cat;

    void Start() {
        // there is need for cat to be active at beginning, so we have to deactive it here. If cat is not active
        // at beginning it causes strange problem with not being able to activate it.
        cat.SetActive(false);

//        logo.SetActive(true);
//        cat.SetActive(true);
//        dinosaur.SetActive(true);
    }

	public void activateLogo(String arg) {
        dinosaur.SetActive(false);
        cat.SetActive(false);
		logo.SetActive(true);
    }

    public void activateDinosaur(String arg) {
        logo.SetActive(false);
        cat.SetActive(false);
        dinosaur.SetActive(true);
    }

    public void activateCat(String arg) {
        logo.SetActive(false);
        dinosaur.SetActive(false);
        cat.SetActive(true);
    }

}
