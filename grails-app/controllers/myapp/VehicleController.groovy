package myapp

import grails.validation.ValidationException
import org.hibernate.Transaction

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.CREATED
import myapp.Vehicle
import grails.transaction.Transactional
@SuppressWarnings(['LineLength'])
@Transactional(readOnly = true)


class VehicleController {

    static namespace = 'scaffolding'

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Vehicle.list(params), model:[vehicleCount: Vehicle.count()]
    }

    def valueEstimateService

    def show(Vehicle vehicle) {
        respond vehicle.list(params), model:[estimatedValue: valueEstimateService.getEstimate(vehicle)]
    }

    @SuppressWarnings(['FactoryMethodName', 'GrailsMAssAssignment'])
    def create() {
        respond new Vehicle(params)
    }

    @Transactional
    def save(Vehicle vehicle) {
        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vehicle.hasErrors()){
            transactionStatus.setRollbackOnly()
            respond vehicle.errors, view: 'create'
        }

        vehicle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect vehicle
            }
            '*' { respond vehicle, [status: CREATED] }
        }
    }

    def edit(Vehicle vehicle) {
        respond vehicle
    }

    @Transactional
    def update(Vehicle vehicle) {
        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        vehicle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect vehicle
            }
            '*'{ respond vehicle, [status: OK] }
        }
    }

    @Transactional
    def delete(Long id) {
        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        vehicle.delete flush true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
