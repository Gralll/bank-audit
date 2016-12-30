(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('GroupRateDetailController', GroupRateDetailController);

    GroupRateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GroupRate', 'RateData', 'LocalRate'];

    function GroupRateDetailController($scope, $rootScope, $stateParams, previousState, entity, GroupRate) {
        var vm = this;

        vm.groupRate = entity;
        vm.previousState = previousState.name;

        vm.groupRateEx = {
            "category" : "MI",
            "id" : 24,
            "indexRate" : "M12",
            "name" : "Определение/коррекция области действия СОИБ",
            "rate" : 0,
            "localRates" : [
                {
                    "category" : "FIRST",
                    "documentation" : null,
                    "execution" : null,
                    "id" : 1,
                    "indexRate" : "М12.1",
                    "necessary" : "Обязательный",
                    "notRated" : null,
                    "question" : "Определены ли, выполняются ли, регистрируются ли и контролируются ли в организации БС РФ процедуры учета структурированных по классам (типам) защищаемых информационных активов?",
                    "rate" : null
                },
                {
                    "category" : "FOURTH",
                    "documentation" : null,
                    "execution" : null,
                    "id" : 1,
                    "indexRate" : "М12.2",
                    "necessary" : "Рекомендуемый",
                    "notRated" : null,
                    "question" : "Проводится ли классификация информационных активов на основании оценок ценности информационных активов для интересов (целей) организации БС РФ, например в соответствии с тяжестью последствий потери свойств ИБ информационных активов?",
                    "rate" : null
                },
                {
                    "category" : "SECOND",
                    "documentation" : null,
                    "execution" : null,
                    "id" : 1,
                    "indexRate" : "М12.3",
                    "necessary" : "Обязательный",
                    "notRated" : null,
                    "question" : "Установлены ли в организации БС РФ критерии отнесения конкретных информационных активов к одному или нескольким типам информационных активов?",
                    "rate" : null
                },
                {
                    "category" : "FIRST",
                    "documentation" : null,
                    "execution" : null,
                    "id" : 1,
                    "indexRate" : "М12.4",
                    "necessary" : "Обязательный",
                    "notRated" : null,
                    "question" : "Определены ли, выполняются ли, регистрируются ли и контролируются ли в организации БС РФ процедуры учета объектов среды для каждого информационного актива и (или) типа информационного актива, покрывающие все уровни информационной инфраструктуры организации БС РФ, определенной в разделе 6 стандарта СТО БР ИББС-1.0?",
                    "rate" : null
                },
                {
                    "category" : "THIRD",
                    "documentation" : null,
                    "execution" : null,
                    "id" : 1,
                    "indexRate" : "М12.5",
                    "necessary" : "Обязательный",
                    "notRated" : null,
                    "question" : "Определены ли в организации БС РФ роли по учету информационных активов и учету объектов среды для каждого информационного актива и (или) типа информационного актива, покрывающие все уровни информационной инфраструктуры организации БС РФ, определенной в разделе 6 стандарта СТО БР ИББС-1.0?",
                    "rate" : null
                },
                {
                    "category" : "THIRD",
                    "documentation" : null,
                    "execution" : null,
                    "id" : 1,
                    "indexRate" : "М12.6",
                    "necessary" : "Обязательный",
                    "notRated" : null,
                    "question" : "Назначены ли в организации БС РФ ответственные за выполнение ролей по учету информационных активов и учету объектов среды для каждого информационного актива и (или) типа информационного актива, покрывающие все уровни информационной инфраструктуры организации БС РФ, определенной в разделе 6 стандарта СТО БР ИББС-1.0?",
                    "rate" : null
                }
            ]
        };

        var unsubscribe = $rootScope.$on('bankAuditApp:groupRateUpdate', function(event, result) {
            vm.groupRate = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.save = function save () {
            vm.isSaving = true;
            if (vm.groupRate.id !== null) {
                GroupRate.update(vm.groupRate, onSaveSuccess, onSaveError);
            } else {
                GroupRate.save(vm.groupRate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:groupRateUpdate', result);
            //$uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.calcLocalRate = function(localRate, notRated) {
            if (notRated == true) {
                localRate.rate = "Н/О";
            } else {
                if (localRate.category == 'FIRST') {
                    if (localRate.documentation == 'YES') {
                        if (localRate.execution == 'YES') {
                            localRate.rate = 1.0;
                        } else if (localRate.execution == 'ALMOST') {
                            localRate.rate = 0.75;
                        } else if (localRate.execution == 'PARTIALLY') {
                            localRate.rate = 0.5;
                        } else if (localRate.execution == 'NO') {
                            localRate.rate = 0.25;
                        } else {
                            localRate.rate = null;
                        }
                    } else if (localRate.documentation == 'NO') {
                        localRate.rate = 0.0;
                    } else {
                        localRate.rate = null;
                    }
                }
                if (localRate.category == 'SECOND') {
                    if (localRate.documentation == 'YES') {
                        localRate.rate = 1.0;
                    } else if (localRate.documentation == 'NO') {
                        localRate.rate = 0.0;
                    } else {
                        localRate.rate = null;
                    }
                }
                if (localRate.category == 'THIRD') {
                    if (localRate.execution == 'YES') {
                        localRate.rate = 1.0;
                    } else if (localRate.execution == 'PARTIALLY') {
                        localRate.rate = 0.5;
                    } else if (localRate.execution == 'NO') {
                        localRate.rate = 0.0;
                    } else {
                        localRate.rate = null;
                    }
                }
                if (localRate.category == 'FOURTH') {
                    if (localRate.documentation == 'YES') {
                        localRate.rate = 1.0;
                    } else if (localRate.documentation == 'NO') {
                        localRate.rate = 0.0;
                    } else {
                        localRate.rate = null;
                    }
                }
            }

            return localRate.rate;
        };
    }
})();
