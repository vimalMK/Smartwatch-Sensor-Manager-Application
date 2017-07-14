require.config({
  paths: {
    "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min",
    
  },
  shim: {
        'jquery': {
            exports: '$'
        }
    }
});



require(["jquery","commonHost"],function($,j2v8){
  j2v8.input("interger","interger");
  j2v8.input("float","interger");
  $("#output").html("simple composite accessor in J2V8 working !!!!!!: "+j2v8.inputList());
 // j2v8.display("vimal");
});