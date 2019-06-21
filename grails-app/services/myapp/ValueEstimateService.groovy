package myapp

import grails.gorm.transactions.Transactional

@Transactional
class ValueEstimateService {

    def getEstimate(Vehicle vehicle) {

        log.info 'Estimating vehicle value...'

        Math.round (vehicle.name.size() + vehicle.model.name.size() * vehicle.year) * 2
    }
}
