'use strict';

describe('Controller Tests', function() {

    describe('GroupRate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGroupRate, MockRateData, MockLocalRate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGroupRate = jasmine.createSpy('MockGroupRate');
            MockRateData = jasmine.createSpy('MockRateData');
            MockLocalRate = jasmine.createSpy('MockLocalRate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GroupRate': MockGroupRate,
                'RateData': MockRateData,
                'LocalRate': MockLocalRate
            };
            createController = function() {
                $injector.get('$controller')("GroupRateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bankAuditApp:groupRateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
