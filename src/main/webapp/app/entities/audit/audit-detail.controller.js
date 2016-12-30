(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('AuditDetailController', AuditDetailController);

    AuditDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Audit', 'RateData', 'RateResult'];

    function AuditDetailController($scope, $rootScope, $stateParams, previousState, entity, Audit, RateData, RateResult) {
        var vm = this;
        vm.isVisibleRates = false;
        vm.isVisibleResults = true;
        vm.isVisibleRatesTU = false;
        vm.isVisibleRatesMI = false;
        vm.isVisibleRatesPP = false;

        vm.audit = entity;
        vm.auditOne = {
            "groupRates": [
                {
                    "category" : "TU",
                    "id" : 1,
                    "indexRate" : "M1",
                    "name" : "Обеспечение информационной безопасности при назначении и распределении ролей и обеспечении доверия к персоналу",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 2,
                    "indexRate" : "M2",
                    "name" : "Обеспечение информационной безопасности автоматизированных банковских систем на стадиях жизненного цикла",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 3,
                    "indexRate" : "M3",
                    "name" : "Обеспечение информационной безопасности при управлении доступом и регистрации",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 4,
                    "indexRate" : "M4",
                    "name" : "Обеспечение информационной безопасности средствами антивирусной защиты",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 5,
                    "indexRate" : "M5",
                    "name" : "Обеспечение информационной безопасности при использовании ресурсов сети Интернет",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 6,
                    "indexRate" : "M6",
                    "name" : "Обеспечение информационной безопасности при использовании средств криптографической защиты информации",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 7,
                    "indexRate" : "M7",
                    "name" : "Обеспечение информационной безопасности банковских платежных технологических процессов",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 8,
                    "indexRate" : "M1(И)",
                    "name" : "Обеспечение информационной безопасности при назначении и распределении ролей и обеспечении доверия к персоналу",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 9,
                    "indexRate" : "M2(И)",
                    "name" : "Обеспечение информационной безопасности автоматизированных банковских систем на стадиях жизненного цикла",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 10,
                    "indexRate" : "M3(И)",
                    "name" : "Обеспечение информационной безопасности при управлении доступом и регистрации",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 11,
                    "indexRate" : "M4(И)",
                    "name" : "Обеспечение информационной безопасности средствами антивирусной защиты",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 12,
                    "indexRate" : "M5(И)",
                    "name" : "Обеспечение информационной безопасности при использовании ресурсов сети Интернет",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 13,
                    "indexRate" : "M6(И)",
                    "name" : "Обеспечение информационной безопасности при использовании средств криптографической защиты информации",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 14,
                    "indexRate" : "M8",
                    "name" : "Обеспечение информационной безопасности банковских информационных технологических процессов",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 15,
                    "indexRate" : "M1(П)",
                    "name" : "Обеспечение информационной безопасности при назначении и распределении ролей и обеспечении доверия к персоналу",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 16,
                    "indexRate" : "M2(П)",
                    "name" : "Обеспечение информационной безопасности автоматизированных банковских систем на стадиях жизненного цикла",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 17,
                    "indexRate" : "M3(П)",
                    "name" : "Обеспечение информационной безопасности при управлении доступом и регистрации",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 18,
                    "indexRate" : "M4(П)",
                    "name" : "Обеспечение информационной безопасности средствами антивирусной защиты",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 19,
                    "indexRate" : "M5(П)",
                    "name" : "Обеспечение информационной безопасности при использовании ресурсов сети Интернет",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 20,
                    "indexRate" : "M6(П)",
                    "name" : "Обеспечение информационной безопасности при использовании средств криптографической защиты информации",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 21,
                    "indexRate" : "M10",
                    "name" : "Общие требования по обеспечению информационной безопасности банковских технологических процессов, в рамках которых обрабатываются персональные данные",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "TU",
                    "id" : 22,
                    "indexRate" : "M9",
                    "name" : "Общие требования по обработке персональных данных в организации БС РФ",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },

                {
                    "category" : "MI",
                    "id" : 23,
                    "indexRate" : "M11",
                    "name" : "Организация и функционирование службы ИБ организации БС РФ",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "MI",
                    "id" : 24,
                    "indexRate" : "M12",
                    "name" : "Определение/коррекция области действия СОИБ",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "MI",
                    "id" : 25,
                    "indexRate" : "M13",
                    "name" : "Выбор/коррекция подхода к оценке рисков нарушения ИБ и проведению оценки рисков нарушения ИБ",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },

                {
                    "category" : "PP",
                    "id" : 26,
                    "indexRate" : "M28",
                    "name" : "Оценка деятельности руководства организации БС РФ по поддержке функционирования службы ИБ организации БС РФ",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "PP",
                    "id" : 27,
                    "indexRate" : "M29",
                    "name" : "Оценка деятельности руководства организации БС РФ по принятию решений о реализации и эксплуатации СОИБ",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },
                {
                    "category" : "PP",
                    "id" : 28,
                    "indexRate" : "M30",
                    "name" : "Оценка деятельности руководства организации БС РФ по поддержке планирования СОИБ",
                    "rate" : 0,
                    "localRates" : [
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.1",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla1",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.2",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla2",
                            "rate" : null
                        },
                        {
                            "category" : "",
                            "documentation" : null,
                            "execution" : null,
                            "id" : 1,
                            "indexRate" : "M1.3",
                            "necessary" : true,
                            "notRated" : null,
                            "question" : "bla3",
                            "rate" : null
                        }
                    ]

                },

            ]
        };
        vm.previousState = previousState.name;
        vm.diagramm1 = 1;
        vm.diagramm2 = 2;

        var unsubscribe = $rootScope.$on('bankAuditApp:auditUpdate', function(event, result) {
            vm.audit = result;
        });
        $scope.$on('$destroy', unsubscribe);


        this.getRates = function() {
            vm.isVisibleRates = true;
            vm.isVisibleResults = false;
        };

        this.getResults = function () {
            vm.isVisibleRates = false;
            vm.isVisibleResults = true;
        };

        this.getGroupRateProgress = function(groupRate) {
            if (Math.random() > 0.3) {
                groupRate.rate = (0.5 + Math.random() * 0.4 + "").substring(0, 4) ;
                return "100%";
            }
            var rand = 5 + Math.random() * 95;
            rand = Math.round(rand);
            groupRate.rate = (0.5 + Math.random() * 0.4 + "").substring(0, 4) ;
            return rand + "%";
            /*var countRated = 0;
            var countAllRated = 0;
            var sumRates = 0;
            for (var i = 0; i < groupRate.localRates.length; i++) {
                if (!groupRate.localRates[i].notRated) {
                    if (groupRate.localRates[i].rate != null) {
                        countRated++;
                        sumRates += groupRate.localRates[i].rate;
                    }
                    countAllRated++;
                }
            }
            groupRate.rate = sumRates/countAllRated;
            return (countRated / countAllRated * 100) + "%";*/
        }

    }
})();
