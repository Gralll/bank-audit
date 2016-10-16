'use strict';

describe('Controller Tests', function() {

    describe('RateResult Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRateResult, MockRateMatrix, MockAdditionalRate, MockFinalRate, MockEvDiagram, MockGroupDiagram;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRateResult = jasmine.createSpy('MockRateResult');
            MockRateMatrix = jasmine.createSpy('MockRateMatrix');
            MockAdditionalRate = jasmine.createSpy('MockAdditionalRate');
            MockFinalRate = jasmine.createSpy('MockFinalRate');
            MockEvDiagram = jasmine.createSpy('MockEvDiagram');
            MockGroupDiagram = jasmine.createSpy('MockGroupDiagram');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RateResult': MockRateResult,
                'RateMatrix': MockRateMatrix,
                'AdditionalRate': MockAdditionalRate,
                'FinalRate': MockFinalRate,
                'EvDiagram': MockEvDiagram,
                'GroupDiagram': MockGroupDiagram
            };
            createController = function() {
                $injector.get('$controller')("RateResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bankAuditApp:rateResultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
