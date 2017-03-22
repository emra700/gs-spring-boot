angular.
    module('dashboard').
    component('dashboard', {
        templateUrl: "dashboard.template.html",
        bindings: { parent: '=' },
        controller: function($uibModal) {
            $ctrl = this;
            $ctrl.dataForModal = {
                name: 'NameToEdit',
                value: 'ValueToEdit'
            }

            this.removeAssetTemplate = function(servicecontract,assettemplate) {
                var index = servicecontract.assetTemplates.indexOf(assettemplate);
                servicecontract.assetTemplates.splice(index, 1);
            }

            this.addAssetTemplate = function(servicecontract) {
                console.log('addAssetTemplate');
                var newValue = {'name' : '','ali' : ':','keyDefinitions' : [],'attributeDefinitions' : []};
                if(servicecontract.assetTemplates == null) {
                    servicecontract.assetTemplates=[newValue]
                } else  {
                    servicecontract.assetTemplates.push(newValue);
                }
                this.openModal(newValue);
            };

            this.openModal = function(assettemplate) {
                $uibModal.open({
                    component: "assettemplateModal",
                    resolve: {
                        modalData: assettemplate
                    }
                }).result.then(function(result) {
                    console.info("I was closed, so do what I need to do myContent's  controller now.  Result was->");
                    console.info(result);
                }, function(reason) {
                  console.info("I was dimissed, so do what I need to do myContent's controller now.  Reason was->" + reason);
                });
            };
        }
    });

angular.module('assettemplateDetail').component('assettemplateModal', {
    templateUrl: "assettemplate/assettemplate-modal.template.html",
    bindings: {
        modalInstance: "<",
        resolve: "<"
    },
    controller: ['$http','$scope',  function($http, $scope)  {
        var $ctrl = this;
        $ctrl.modalData = $ctrl.resolve.modalData;


        $http.get('../metadata/remoteprovider').then(function(response){
                    $ctrl.remoteproviders = response.data;
                    $ctrl.remoteproviders.map(function(aDef) {
                        if ($ctrl.modalData.remoteProvider.id === aDef.id)
                            $ctrl.modalData.remoteProvider = aDef;
                    });
                }, function(response) {
                    console.log('error');
                    console.log(response);
                });




        $ctrl.handleClose = function() {
            console.info("in handle close");
            $ctrl.modalInstance.close($ctrl.modalData);
        };

        $ctrl.handleDismiss = function() {
            console.info("in handle dismiss");
            $ctrl.modalInstance.dismiss("cancel");
        };
    }]
});