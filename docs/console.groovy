import myapp.*

def vehicles = Vehicle.list()

println vehicle.size()

def pickup = Vehicle.findByName("Pickup")

println pickup.name
println pickup.make.name
println pickup.model.name

def nissan = Make.findByName("Nissan")

def nissans = Vehicle.findAllByMake(nissan)

println nissans.size()