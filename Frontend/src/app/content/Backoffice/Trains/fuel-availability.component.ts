import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../_service/user.service";
import {NotifierService} from "angular-notifier"
import {BackendService} from "../../../_service/BackendService";


@Component({
  selector: 'app-fuel-availability',
  templateUrl: './fuel-availability.component.html',
  styleUrls: ['./fuel-availability.component.css']
})
export class FuelAvailabilityComponent implements OnInit {

  trains = []
  train;
  isUpdate=false
   dataDB

  constructor(private backendService:BackendService,private userS: UserService,private notifierService:NotifierService) {
    this.train=backendService.newTrain()
  }

  ngOnInit(): void {
    this.getTrains() //get trains

  }

  addTrains(){ //add train
    this.train.status="active"
    this.backendService.addTrain(this.train).subscribe(fuelStocks => {
      console.log(fuelStocks)
      this.train=this.backendService.newTrain() //asign train obj
      this.ngOnInit()
    });
  }

  updateTrains(){ //update train
    this.train.status="active"
    this.backendService.updateTrain(this.train.id,this.train).subscribe(fuelStocks => {
      console.log(fuelStocks)
      this.isUpdate=false
      this.train=this.backendService.newTrain() //asign train obj
      this.ngOnInit()
    });

  }

  deleteTrains(data){ //delete train
    console.log(data);
    this.dataDB=data
    this.dataDB.status="inactive"
    this.backendService.deleteTrain(this.dataDB.id, this.dataDB).subscribe(
      fuelStocks => {
        console.log(fuelStocks);
        this.ngOnInit();
        this.notifierService.notify("success","success");
      },
      error => {
        console.error("An error occurred1:", error.error);
        this.notifierService.notify("error", error.error);
        this.ngOnInit();
        // Handle the error here (e.g., show a user-friendly message)
      }
    );

  }

  update(data){ //update trains
    console.log("sss",data)
    this.isUpdate=true
    const depature = new Date(data.depatre_Time);
    const datearrival = new Date(data.arrival_Time);
    const isoDateArrivalTime = datearrival.toISOString().slice(0, 19);
    const isoDateDepatureTime = depature.toISOString().slice(0, 19);
    this.train.id=data.id
    this.train.number=data.number
    this.train.depatre_Time=isoDateDepatureTime
    this.train.arrival_Time=isoDateArrivalTime
    this.train.depatre_Station=data.depatre_Station
    this.train.arrival_Station=data.arrival_Station
    this.train.number=data.number
  }

  getTrains(){ //get trains
    this.backendService.getTrain().subscribe(fuelStocks => {
      console.log(fuelStocks)
      this.trains=fuelStocks
    });
  }

}
