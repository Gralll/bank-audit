'use strict';

describe('Controller Tests', function() {

    describe('Audit Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAudit, MockRateData, MockRateResult;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAudit = jasmine.createSpy('MockAudit');
            MockRateData = jasmine.createSpy('MockRateData');
            MockRateResult = jasmine.createSpy('MockRateResult');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Audit': MockAudit,
                'RateData': MockRateData,
                'RateResult': MockRateResult
            };
            createController = function() {
                $injector.get('$controller')("AuditDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bankAuditApp:auditUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
