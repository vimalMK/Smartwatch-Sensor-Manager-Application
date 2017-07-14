


var setup=function()
{
 display("alertAccessor setup with J2V8 Accessor");
 this.input("dataRate", 'type:number');
 this.output("xAcc", 'type:number');
 this.output("yAcc", 'type:number');
 this.output("zAcc", 'type:number');

 return x;
};

var initialize = function() {

    display("alertAccessor initialized");

};

var fire=function()
{
w.display("fire instantiated!!");
var ll=w.getAccessorModule('fallDetectionAccessor');
return ll;
};