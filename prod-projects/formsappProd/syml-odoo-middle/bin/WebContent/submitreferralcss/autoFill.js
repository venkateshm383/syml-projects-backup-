(function(){var JSTORAGE_VERSION="0.4.0",$=window.jQuery||window.$||(window.$={}),JSON={parse:window.JSON&&(window.JSON.parse||window.JSON.decode)||String.prototype.evalJSON&&function(str){return String(str).evalJSON()}||$.parseJSON||$.evalJSON,stringify:Object.toJSON||window.JSON&&(window.JSON.stringify||window.JSON.encode)||$.toJSON};if(!JSON.parse||!JSON.stringify)throw new Error("No JSON support found, include //cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.js to page");var _storage={__jstorage_meta:{CRC32:{}}},
_storage_service={jStorage:"{}"},_storage_elm=null,_storage_size=0,_backend=false,_observers={},_observer_timeout=false,_observer_update=0,_pubsub_observers={},_pubsub_last=+new Date,_ttl_timeout,_XMLService={isXML:function(elm){var documentElement=(elm?elm.ownerDocument||elm:0).documentElement;return documentElement?documentElement.nodeName!=="HTML":false},encode:function(xmlNode){if(!this.isXML(xmlNode))return false;try{return(new XMLSerializer).serializeToString(xmlNode)}catch(E1){try{return xmlNode.xml}catch(E2){}}return false},
decode:function(xmlString){var dom_parser="DOMParser"in window&&(new DOMParser).parseFromString||window.ActiveXObject&&function(_xmlString){var xml_doc=new ActiveXObject("Microsoft.XMLDOM");xml_doc.async="false";xml_doc.loadXML(_xmlString);return xml_doc},resultXML;if(!dom_parser)return false;resultXML=dom_parser.call("DOMParser"in window&&new DOMParser||window,xmlString,"text/xml");return this.isXML(resultXML)?resultXML:false}};function _init(){var localStorageReallyWorks=false;if("localStorage"in
window)try{window.localStorage.setItem("_tmptest","tmpval");localStorageReallyWorks=true;window.localStorage.removeItem("_tmptest")}catch(BogusQuotaExceededErrorOnIos5){}if(localStorageReallyWorks)try{if(window.localStorage){_storage_service=window.localStorage;_backend="localStorage";_observer_update=_storage_service.jStorage_update}}catch(E3){}else if("globalStorage"in window)try{if(window.globalStorage){_storage_service=window.globalStorage[window.location.hostname];_backend="globalStorage";_observer_update=
_storage_service.jStorage_update}}catch(E4){}else{_storage_elm=document.createElement("link");if(_storage_elm.addBehavior){_storage_elm.style.behavior="url(#default#userData)";document.getElementsByTagName("head")[0].appendChild(_storage_elm);try{_storage_elm.load("jStorage")}catch(E){_storage_elm.setAttribute("jStorage","{}");_storage_elm.save("jStorage");_storage_elm.load("jStorage")}var data="{}";try{data=_storage_elm.getAttribute("jStorage")}catch(E5){}try{_observer_update=_storage_elm.getAttribute("jStorage_update")}catch(E6){}_storage_service.jStorage=
data;_backend="userDataBehavior"}else{_storage_elm=null;return}}_load_storage();_handleTTL();_setupObserver();_handlePubSub();if("addEventListener"in window)window.addEventListener("pageshow",function(event){if(event.persisted)_storageObserver()},false)}function _reloadData(){var data="{}";if(_backend=="userDataBehavior"){_storage_elm.load("jStorage");try{data=_storage_elm.getAttribute("jStorage")}catch(E5){}try{_observer_update=_storage_elm.getAttribute("jStorage_update")}catch(E6){}_storage_service.jStorage=
data}_load_storage();_handleTTL();_handlePubSub()}function _setupObserver(){if(_backend=="localStorage"||_backend=="globalStorage")if("addEventListener"in window)window.addEventListener("storage",_storageObserver,false);else document.attachEvent("onstorage",_storageObserver);else if(_backend=="userDataBehavior")setInterval(_storageObserver,1E3)}function _storageObserver(){var updateTime;clearTimeout(_observer_timeout);_observer_timeout=setTimeout(function(){if(_backend=="localStorage"||_backend==
"globalStorage")updateTime=_storage_service.jStorage_update;else if(_backend=="userDataBehavior"){_storage_elm.load("jStorage");try{updateTime=_storage_elm.getAttribute("jStorage_update")}catch(E5){}}if(updateTime&&updateTime!=_observer_update){_observer_update=updateTime;_checkUpdatedKeys()}},25)}function _checkUpdatedKeys(){var oldCrc32List=JSON.parse(JSON.stringify(_storage.__jstorage_meta.CRC32)),newCrc32List;_reloadData();newCrc32List=JSON.parse(JSON.stringify(_storage.__jstorage_meta.CRC32));
var key,updated=[],removed=[];for(key in oldCrc32List)if(oldCrc32List.hasOwnProperty(key)){if(!newCrc32List[key]){removed.push(key);continue}if(oldCrc32List[key]!=newCrc32List[key]&&String(oldCrc32List[key]).substr(0,2)=="2.")updated.push(key)}for(key in newCrc32List)if(newCrc32List.hasOwnProperty(key))if(!oldCrc32List[key])updated.push(key);_fireObservers(updated,"updated");_fireObservers(removed,"deleted")}function _fireObservers(keys,action){keys=[].concat(keys||[]);if(action=="flushed"){keys=
[];for(var key in _observers)if(_observers.hasOwnProperty(key))keys.push(key);action="deleted"}for(var i=0,len=keys.length;i<len;i++)if(_observers[keys[i]])for(var j=0,jlen=_observers[keys[i]].length;j<jlen;j++)_observers[keys[i]][j](keys[i],action)}function _publishChange(){var updateTime=(+new Date).toString();if(_backend=="localStorage"||_backend=="globalStorage")_storage_service.jStorage_update=updateTime;else if(_backend=="userDataBehavior"){_storage_elm.setAttribute("jStorage_update",updateTime);
_storage_elm.save("jStorage")}_storageObserver()}function _load_storage(){if(_storage_service.jStorage)try{_storage=JSON.parse(String(_storage_service.jStorage))}catch(E6){_storage_service.jStorage="{}"}else _storage_service.jStorage="{}";_storage_size=_storage_service.jStorage?String(_storage_service.jStorage).length:0;if(!_storage.__jstorage_meta)_storage.__jstorage_meta={};if(!_storage.__jstorage_meta.CRC32)_storage.__jstorage_meta.CRC32={}}function _save(){_dropOldEvents();try{_storage_service.jStorage=
JSON.stringify(_storage);if(_storage_elm){_storage_elm.setAttribute("jStorage",_storage_service.jStorage);_storage_elm.save("jStorage")}_storage_size=_storage_service.jStorage?String(_storage_service.jStorage).length:0}catch(E7){}}function _checkKey(key){if(!key||typeof key!="string"&&typeof key!="number")throw new TypeError("Key name must be string or numeric");if(key=="__jstorage_meta")throw new TypeError("Reserved key name");return true}function _handleTTL(){var curtime,i,TTL,CRC32,nextExpire=
Infinity,changed=false,deleted=[];clearTimeout(_ttl_timeout);if(!_storage.__jstorage_meta||typeof _storage.__jstorage_meta.TTL!="object")return;curtime=+new Date;TTL=_storage.__jstorage_meta.TTL;CRC32=_storage.__jstorage_meta.CRC32;for(i in TTL)if(TTL.hasOwnProperty(i))if(TTL[i]<=curtime){delete TTL[i];delete CRC32[i];delete _storage[i];changed=true;deleted.push(i)}else if(TTL[i]<nextExpire)nextExpire=TTL[i];if(nextExpire!=Infinity)_ttl_timeout=setTimeout(_handleTTL,nextExpire-curtime);if(changed){_save();
_publishChange();_fireObservers(deleted,"deleted")}}function _handlePubSub(){var i,len;if(!_storage.__jstorage_meta.PubSub)return;var pubelm,_pubsubCurrent=_pubsub_last;for(i=len=_storage.__jstorage_meta.PubSub.length-1;i>=0;i--){pubelm=_storage.__jstorage_meta.PubSub[i];if(pubelm[0]>_pubsub_last){_pubsubCurrent=pubelm[0];_fireSubscribers(pubelm[1],pubelm[2])}}_pubsub_last=_pubsubCurrent}function _fireSubscribers(channel,payload){if(_pubsub_observers[channel])for(var i=0,len=_pubsub_observers[channel].length;i<
len;i++)_pubsub_observers[channel][i](channel,JSON.parse(JSON.stringify(payload)))}function _dropOldEvents(){if(!_storage.__jstorage_meta.PubSub)return;var retire=+new Date-2E3;for(var i=0,len=_storage.__jstorage_meta.PubSub.length;i<len;i++)if(_storage.__jstorage_meta.PubSub[i][0]<=retire){_storage.__jstorage_meta.PubSub.splice(i,_storage.__jstorage_meta.PubSub.length-i);break}if(!_storage.__jstorage_meta.PubSub.length)delete _storage.__jstorage_meta.PubSub}function _publish(channel,payload){if(!_storage.__jstorage_meta)_storage.__jstorage_meta=
{};if(!_storage.__jstorage_meta.PubSub)_storage.__jstorage_meta.PubSub=[];_storage.__jstorage_meta.PubSub.unshift([+new Date,channel,payload]);_save();_publishChange()}function murmurhash2_32_gc(str,seed){var l=str.length,h=seed^l,i=0,k;while(l>=4){k=str.charCodeAt(i)&255|(str.charCodeAt(++i)&255)<<8|(str.charCodeAt(++i)&255)<<16|(str.charCodeAt(++i)&255)<<24;k=(k&65535)*1540483477+(((k>>>16)*1540483477&65535)<<16);k^=k>>>24;k=(k&65535)*1540483477+(((k>>>16)*1540483477&65535)<<16);h=(h&65535)*1540483477+
(((h>>>16)*1540483477&65535)<<16)^k;l-=4;++i}switch(l){case 3:h^=(str.charCodeAt(i+2)&255)<<16;case 2:h^=(str.charCodeAt(i+1)&255)<<8;case 1:h^=str.charCodeAt(i)&255;h=(h&65535)*1540483477+(((h>>>16)*1540483477&65535)<<16)}h^=h>>>13;h=(h&65535)*1540483477+(((h>>>16)*1540483477&65535)<<16);h^=h>>>15;return h>>>0}$.jStorage={version:JSTORAGE_VERSION,set:function(key,value,options){_checkKey(key);options=options||{};if(typeof value=="undefined"){this.deleteKey(key);return value}if(_XMLService.isXML(value))value=
{_is_xml:true,xml:_XMLService.encode(value)};else if(typeof value=="function")return undefined;else if(value&&typeof value=="object")value=JSON.parse(JSON.stringify(value));_storage[key]=value;_storage.__jstorage_meta.CRC32[key]="2."+murmurhash2_32_gc(JSON.stringify(value));this.setTTL(key,options.TTL||0);_fireObservers(key,"updated");return value},get:function(key,def){_checkKey(key);if(key in _storage)if(_storage[key]&&typeof _storage[key]=="object"&&_storage[key]._is_xml)return _XMLService.decode(_storage[key].xml);
else return _storage[key];return typeof def=="undefined"?null:def},deleteKey:function(key){_checkKey(key);if(key in _storage){delete _storage[key];if(typeof _storage.__jstorage_meta.TTL=="object"&&key in _storage.__jstorage_meta.TTL)delete _storage.__jstorage_meta.TTL[key];delete _storage.__jstorage_meta.CRC32[key];_save();_publishChange();_fireObservers(key,"deleted");return true}return false},setTTL:function(key,ttl){var curtime=+new Date;_checkKey(key);ttl=Number(ttl)||0;if(key in _storage){if(!_storage.__jstorage_meta.TTL)_storage.__jstorage_meta.TTL=
{};if(ttl>0)_storage.__jstorage_meta.TTL[key]=curtime+ttl;else delete _storage.__jstorage_meta.TTL[key];_save();_handleTTL();_publishChange();return true}return false},getTTL:function(key){var curtime=+new Date,ttl;_checkKey(key);if(key in _storage&&_storage.__jstorage_meta.TTL&&_storage.__jstorage_meta.TTL[key]){ttl=_storage.__jstorage_meta.TTL[key]-curtime;return ttl||0}return 0},flush:function(){_storage={__jstorage_meta:{CRC32:{}}};_save();_publishChange();_fireObservers(null,"flushed");return true},
storageObj:function(){function F(){}F.prototype=_storage;return new F},index:function(){var index=[],i;for(i in _storage)if(_storage.hasOwnProperty(i)&&i!="__jstorage_meta")index.push(i);return index},storageSize:function(){return _storage_size},currentBackend:function(){return _backend},storageAvailable:function(){return!!_backend},listenKeyChange:function(key,callback){_checkKey(key);if(!_observers[key])_observers[key]=[];_observers[key].push(callback)},stopListening:function(key,callback){_checkKey(key);
if(!_observers[key])return;if(!callback){delete _observers[key];return}for(var i=_observers[key].length-1;i>=0;i--)if(_observers[key][i]==callback)_observers[key].splice(i,1)},subscribe:function(channel,callback){channel=(channel||"").toString();if(!channel)throw new TypeError("Channel not defined");if(!_pubsub_observers[channel])_pubsub_observers[channel]=[];_pubsub_observers[channel].push(callback)},publish:function(channel,payload){channel=(channel||"").toString();if(!channel)throw new TypeError("Channel not defined");
_publish(channel,payload)},reInit:function(){_reloadData()}};_init()})(jQuery);

(function ($) {
    $.fn.autoFill = function (options) {
        var identifier = $.map(this, function (obj, i) {
            return $(obj).attr("id") + $(obj).attr("name")
        }).join();
        var autoFill = AutoFill.getInstance(identifier);
        autoFill.protect(this, options);
        return autoFill
    };

    var jotFormBrowserStorage =
    {
        debugIsOn: false,

        isAvailable: function()
        {
            if (typeof $.jStorage === "object") return true;
            else return false;
        },
        set: function (key, value)
        {
            $.jStorage.set(key, value + "");
        },
        get: function (key)
        {
            var result = $.jStorage.get(key);
            return result ? result.toString() : result;
        },
        remove: function (key)
        {
            $.jStorage.deleteKey(key);
        },
        timeNow: function()
        {
            return +new Date;
        },
        secToMil: function(sec)
        {
            return ( Number(sec) * 1000 );
        },
        setTTL: function(formID, ttl)
        {
            var c_ttl = this.getTTLs();
            if ( !c_ttl ) c_ttl = {};

            //if the formID is already in the ttl list
            //check each key if needs to be delete
            var timeNow = this.timeNow();
            if ( c_ttl.hasOwnProperty(formID) )
            {
                //get the save ttl(registered time+registered ttl value)
                var savedTTL = c_ttl[formID].ttl;
                var savedRAW = c_ttl[formID].raw;

                //get the registered time
                var originalTime = Number(savedTTL - savedRAW);

                //add the temp registered time to current ttl reading
                var nowTTL = Number(originalTime + this.secToMil(ttl));

                //compare if the saved ttl is similar to the nowTTL
                //if so do nothing, otherwise over write the data with new ttl value
                if ( savedTTL === nowTTL )
                {
                    if(window.console && this.debugIsOn) window.console.log('Already attached!! Time set:', c_ttl[formID].ttl, "Time now:", timeNow, "Remaining seconds:", (c_ttl[formID].ttl - timeNow) );
                    return null;
                }
                else
                {
                    if(window.console && this.debugIsOn) window.console.log('Not similar TTL, updating!! Time set:', savedTTL, nowTTL );
                }
            }

            c_ttl[ formID ] = { ttl: timeNow + this.secToMil(ttl), raw: this.secToMil(ttl) };
            if(window.console && this.debugIsOn) window.console.log('Attaching!! Time set:', c_ttl[formID].ttl, "Time now:", timeNow, "Remaining seconds:", (c_ttl[formID].ttl - timeNow) );
            this.saveCurrentTTL(c_ttl);
        },
        getTTLs: function()
        {
            var ttls = JSON.parse( this.get('formsTTL') );
            if ( !ttls ) return null;
            return ttls;
        },
        saveCurrentTTL: function(ttl_arr)
        {
            this.set("formsTTL", JSON.stringify( ttl_arr ) );
        },
        checkAndDestroyTTL: function(curr_ttl)
        {
            var forms_ttl = this.getTTLs();
            var _index = $.jStorage.index();
            if ( !forms_ttl ) return null;

            if(window.console && this.debugIsOn) window.console.log("Current formsTTL registered:", forms_ttl );
            for ( var i in forms_ttl )
            {
                var form_ID_name = i;
                var form_ttl_val = forms_ttl[i].ttl;
                var current_time = this.timeNow();
                var formDeleted = false;

                if ( form_ttl_val < current_time )
                {
                    //delete all occurene if this formID to index
                    for( var x in _index )
                    {
                        //if string and the form ID and name is found, remove it - because this is old value
                        if ( typeof _index[x] == "string" && ( _index[x].indexOf(form_ID_name) >= 0 ) )
                        {
                            this.remove(_index[x]);
                            if(window.console && this.debugIsOn) window.console.log('Removed key', _index[x], " of ",form_ID_name );
                            formDeleted = true;
                        }
                    }

                    //if form data is deleted remove it from the formsTTL key
                    if ( formDeleted ) delete forms_ttl[i];
                    
                    this.saveCurrentTTL( forms_ttl );
                }
            }
        },
        flushOldFormData: function()
        {
            if(window.console && this.debugIsOn) window.console.log('Trying to flush old forms..');
            //get all index - registered keys
            var _index = $.jStorage.index();
            var _oldForms = [];
            for( var key in _index )
            {
                //if some jotform signature is found
                if ( typeof _index[key] == "string" && ( _index[key].indexOf("jotform_autoFill") >= 0 ) )
                {
                    this.remove(_index[key]);
                    _oldForms.push(_index[key]);
                }
            }

            if ( _oldForms.length > 0 )
            {
                if(window.console && this.debugIsOn) window.console.log("Some old forms are existed: ", _oldForms);
            }
            else
            {
                if(window.console && this.debugIsOn) window.console.log("No old forms detected.");
            }
        }
    };

    AutoFill = function () {
        var params = {
            instantiated: [],
            started: []
        };

        function init(identifier) {
            return {
                setInstanceIdentifier: function (identifier) {
                    this.identifier = identifier
                },
                getInstanceIdentifier: function () {
                    return this.identifier
                },
                setInitialOptions: function (options) {
                    var defaults = {
                        excludeFields: [],
                        customKeyPrefix: "_jF",
                        locationBased: false,
                        allowBindOnChange: true,
                        timeout: 4,
                        ttl: 86400, //1day
                        autoRelease: true,
                        onBeforeSave: function () {},
                        onSave: function () {},
                        onBeforeRestore: function () {},
                        onRestore: function () {},
                        onRelease: function () {}
                    };
                    this.options = this.options || $.extend(defaults, options);
                    this.jotFormBrowserStorage = jotFormBrowserStorage
                },
                setOptions: function (options) {
                    this.options = this.options || this.setInitialOptions(options);
                    this.options = $.extend(this.options, options)
                },
                protect: function (targets, options) {
                    this.setOptions(options);
                    targets = targets || {};
                    var self = this;
                    self.stopSaving = false;
                    this.targets = this.targets || [];
                    this.href = location.hostname + location.pathname + location.search + location.hash;
                    this.targets = $.merge(this.targets, targets);
                    this.targets = $.unique(this.targets);
                    this.targets = $(this.targets);
                    if (!this.jotFormBrowserStorage.isAvailable()) return false;
                    var callback_result = self.options.onBeforeRestore.call(self);
                    if (callback_result ===
                        undefined || callback_result) self.restoreAllData();
                    if (this.options.autoRelease) self.bindReleaseData();
                    if (!params.started[this.getInstanceIdentifier()]) {
                        self.bindSaveData();
                        params.started[this.getInstanceIdentifier()] = true
                    }
                },
                bindSaveData: function () {
                    var self = this;
                    if (!self.stopSaving) {
                        if (self.options.timeout) self.saveDataByTimeout();
                        self.targets.each(function () {
                            var targetFormIdAndName = $(this).attr("name");
                            var fieldsToProtect = self.getProtectedFields($(this));
                            fieldsToProtect.each(function () {
                                var field = $(this);
                                var prefix = (self.options.locationBased ? self.href : "") + targetFormIdAndName + field.attr("name") + self.options.customKeyPrefix;
                                if (field.is(":text") || field.is("textarea"))
                                    if (!self.options.timeout) self.bindSaveDataImmediately(field, prefix);
                                self.bindSaveDataOnChange(field, prefix)
                            })
                        })
                    }
                },
                getProtectedFields: function(target) {
                    var fields = target.find(":input").not(":submit").not(":reset").not(":button").not(":file").not(":password").not($$('[name*="cc_"]'));
                    $.each(this.options.excludeFields, function(index, excluded) {
                        fields = fields.not("#" + excluded).not("[name=" + excluded + "]");
                    });
                    return fields;
                },
                saveAllData: function () {

                    if(!JotForm.autoFillDeployed) return; //don't let the change event trigger save until setup has finished

                    var self = this;
                    if ($.isFunction(self.options.onBeforeSave)) self.options.onBeforeSave.call(self);
                    if (!self.stopSaving) {
                        self.savedData = {};
                        self.targets.each(function (index) {
                            var targetFormIdAndName = $(this).attr("name");
                            var fieldsToProtect = self.getProtectedFields($(this));
                            self.savedData[index] = {
                                protectedfields: fieldsToProtect,
                                protectedfieldsdata: {}
                            };
                            if ( self.options.ttl ) jotFormBrowserStorage.setTTL(targetFormIdAndName, self.options.ttl);
                            fieldsToProtect.each(function (index2) {
                                var field = $(this);
                                if (field.attr("name") === undefined) return true;
                                var fieldIdAndName = field.attr("id") + field.attr("name");
                                var prefix = (self.options.locationBased ? self.href : "") + targetFormIdAndName + fieldIdAndName + self.options.customKeyPrefix;
                                var value = field.val();
                                self.savedData[index].protectedfieldsdata[index2] = {
                                    field: field,
                                    fieldid: fieldIdAndName,
                                    prefix: prefix,
                                    value: value
                                };
                                if (field.is(":checkbox")) {
                                    if (field.attr("name").indexOf("[") !== -1) {
                                        value = [];
                                        $("[name='" + field.attr("name") + "']:checked").each(function () {
                                            value.push($(this).val())
                                        })
                                    } else value =
                                        field.is(":checked");
                                    self.saveTojotFormBrowserStorage(prefix, value, false)
                                } else if (field.is(":radio")) {
                                    if (field.is(":checked")) {
                                        value = field.val();
                                        self.saveTojotFormBrowserStorage(prefix, value, false)
                                    }
                                } else self.saveTojotFormBrowserStorage(prefix, value, false)
                            });
                        });
                        if ($.isFunction(self.options.onSave)) self.options.onSave.call(self)
                    }
                },
                restoreAllData: function () {
                    var self = this;
                    var restored = false;
                    if ($.isFunction(self.options.onBeforeRestore)) self.options.onBeforeRestore.call(self);

                    //flush data from old autofill forms - temporary
                    jotFormBrowserStorage.flushOldFormData();

                    //kill TTLs first that is assigned to data form
                    if ( self.options.ttl ) jotFormBrowserStorage.checkAndDestroyTTL(self.options.ttl);

                    self.restoredData = {};
                    self.targets.each(function (index) {
                        var targetFormIdAndName = $(this).attr("name");
                        var fieldsToProtect = self.getProtectedFields($(this));
                        self.restoredData[index] = {
                            protectedfields: fieldsToProtect,
                            protectedfieldsdata: {}
                        };
                        fieldsToProtect.each(function (index2) {
                            var field = $(this);
                            var fieldIdAndName = field.attr("id") + field.attr("name");
                            var prefix =
                                (self.options.locationBased ? self.href : "") + targetFormIdAndName + fieldIdAndName + self.options.customKeyPrefix;
                            var resque = self.jotFormBrowserStorage.get(prefix);
                            if (resque) {
                                self.restoreFieldsData(field, resque);
                                restored = true
                            }
                            self.restoredData[index].protectedfieldsdata[index2] = {
                                field: field,
                                prefix: prefix,
                                newinputvalue: resque
                            }
                        });
                    });
                    if (restored && $.isFunction(self.options.onRestore)) self.options.onRestore.call(self)
                },
                restoreFieldsData: function (field, resque) {
                    if (field.attr("name") === undefined) return false;
                    if (field.is(":checkbox") && resque !== "false" && field.attr("name").indexOf("[") === -1) field.attr("checked", "checked");
                    else if (field.is(":checkbox") && resque === "false" && field.attr("name").indexOf("[") === -1) field.removeAttr("checked");
                    else if (field.is(":radio")) {
                        if (field.val() === resque) field.attr("checked", "checked")
                    } else if (field.attr("name").indexOf("[") === -1) field.val(resque);
                    else {
                        resque = resque.split(",");
                        field.val(resque)
                    }
                },
                bindSaveDataImmediately: function (field, prefix) {
                    var self = this;
                    if (!self.stopSaving)
                        if ("onpropertychange" in
                            field) field.get(0).onpropertychange = function () {
                            self.saveTojotFormBrowserStorage(prefix, field.val())
                        };
                        else field.get(0).oninput = function () {
                            self.saveTojotFormBrowserStorage(prefix, field.val())
                        }
                },
                saveTojotFormBrowserStorage: function (key, value, fireCallback) {
                    var self = this;
                    if (!self.stopSaving) {
                        fireCallback = fireCallback === undefined ? true : fireCallback;
                        this.jotFormBrowserStorage.set(key, value);
                        if (fireCallback && value !== "" && $.isFunction(this.options.onSave)) this.options.onSave.call(self)
                    }
                },
                bindSaveDataOnChange: function (field, prefix) {
                    var self = this;
                    if ( self.options.allowBindOnChange )
                    {
                        if (!self.stopSaving) {
                            field.change(function () {
                                self.saveAllData();
                            });
                        }
                    }
                },
                saveDataByTimeout: function () {
                    var self = this;
                    if (!self.stopSaving) {
                        var targetForms = self.targets;
                        setTimeout(function (targetForms) {
                            function timeout() {
                                self.saveAllData();
                                setTimeout(timeout, self.options.timeout * 1E3)
                            }
                            return timeout
                        }(targetForms), self.options.timeout * 1E3)
                    }
                },
                bindReleaseData: function () {
                    var self = this;
                    self.targets.each(function (i) {
                        var target = $(this);
                        var fieldsToProtect = self.getProtectedFields(target);
                        var formIdAndName = target.attr("id") + target.attr("name");
                        $(this).bind("submit", function () {
                            self.releaseData(formIdAndName, fieldsToProtect)
                        }).bind("reset", function () {
                            self.releaseData(formIdAndName, fieldsToProtect, true)
                        })
                    })
                },
                manuallyReleaseData: function () {
                    var self = this;
                    self.stopSavingData();
                    self.targets.each(function (i) {
                        var target = $(this);
                        var fieldsToProtect = self.getProtectedFields(target);
                        var formIdAndName = target.attr("id") + target.attr("name");
                        self.releaseData(formIdAndName, fieldsToProtect)
                    })
                },
                stopSavingData: function () {
                    var self = this;
                    self.stopSaving = true
                },
                releaseData: function (targetFormIdAndName, fieldsToProtect, clearfields) {
                    var released = false;
                    var self = this;
                    fieldsToProtect.each(function () {
                        var field = $(this);
                        if (clearfields)
                            if (field.is(":radio") || field.is(":checkbox")) field.prop("checked", false).attr("checked", false).removeAttr("checked");
                        var fieldIdAndName =
                            field.attr("id") + field.attr("name");
                        var prefix = (self.options.locationBased ? self.href : "") + targetFormIdAndName + fieldIdAndName + self.options.customKeyPrefix;
                        self.jotFormBrowserStorage.remove(prefix);
                        released = true
                    });
                    if (released && $.isFunction(self.options.onRelease)) self.options.onRelease.call(self)
                }
            }
        }
        return {
            getInstance: function (identifier) {
                if (!params.instantiated[identifier]) {
                    params.instantiated[identifier] = init();
                    params.instantiated[identifier].setInstanceIdentifier(identifier);
                    params.instantiated[identifier].setInitialOptions()
                }
                if (identifier) return params.instantiated[identifier];
                return params.instantiated[identifier]
            },
            free: function () {
                params = {};
                return null
            }
        }
    }()
})(jQuery);