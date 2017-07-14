exports.setup = function() {
    display("MSBandAccessor setup with J2V8 Accessor");
    v8Display();
    getYAccData();

    this.input("dataRate", {"type":"number", "options":[16,32,128], "value":16});
    this.output("xAcc", {"type":"number"});
    this.output("yAcc", {"type":"number"});
    this.output("zAcc", {"type":"number"});
    connectToBand(this.get("dataRate"));
};



exports.initialize = function() {

    display("MSBandAccessor initialized");

};

exports.fire = function() {
    display("MSBandAccessor fired");
    display((getTimeAccData()).toString());
};