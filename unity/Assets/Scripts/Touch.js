#pragma strict

//
private var childRenderer : Renderer;
private var imageTargetRenderer : Renderer;
private var animationComponent : Animation;
private var thirdPersonController : ThirdPersonControllerAllosaur;
private static var DINOSAUR_OBJECT_NAME : String = "Allosaurus_03_Controller";

function Start () {
    // If object is visible (turn on by Vuforia in DefaultTrackableEventHandler.cs) then its childs' renderers are enabled.
    // If not visible then renderers
    // are not enabled. We have to check only one of child renderers
    // (every of them are switched when object becomes visible/invisible).
    var renderers : Renderer[] = GetComponentsInChildren.<Renderer>();
    if (renderers != null && renderers.length > 0) {
        print("Object" + this.name + " childs renderers count: " + renderers.length);
        childRenderer = renderers[0];
    }
    //for (var renderer: Renderer in renderers) {
    //    print("Renderer of object child [" + this.name + "]: " + renderer.enabled);
    //}
    //animationComponent = GetComponent.<Animation>();
    //print("Animation: " + animationComponent);
    thirdPersonController = GetComponent.<ThirdPersonControllerAllosaur>();
    print("Third person controller dinosaur: " + thirdPersonController);

}

function Update() {
    // Check if object is displayed by Vuforia AR (by checking renderer's enable parameter of one of its children):
    if (childRenderer != null && childRenderer.enabled) {
        //if (this.GetComponent.<Renderer>())
        if (Input.touchCount > 0 && Input.GetTouch(0).phase == TouchPhase.Began) {
            // Construct a ray from the current touch coordinates
            var ray: Ray = Camera.main.ScreenPointToRay(Input.GetTouch(0).position);
            var hit: RaycastHit;
            // Print info if hit
            print("Touch detected by object: " + this.name);
            if (Physics.Raycast(ray, hit)) {
                print("Object hit name: " + hit.collider.gameObject.name);
                print("Touch script on object: " + this.name);
                switch (hit.collider.gameObject.name) {
                    case DINOSAUR_OBJECT_NAME:
                        print("Start animation for the dinousaur");
                        //animationComponent["Allosaurus_Jump"].wrapMode = WrapMode.Once;
                        //animationComponent["Allosaurus_Jump"].blendMode = AnimationBlendMode.Blend;
                        //animationComponent.Play("Allosaurus_Jump");
                        //var thirdPersonControllerDinousaur = GetComponent.<ThirdPersonController>();
                        thirdPersonController.bite();
                        break;
                }
            }
        }
    }
}
