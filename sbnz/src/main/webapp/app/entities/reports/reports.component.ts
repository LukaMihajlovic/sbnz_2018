import { Component, OnInit } from '@angular/core';
import {ReportsService} from "./reports.service";
import {Patient} from "../patient/patient.model";
import {JhiTrackerService} from "../../shared/tracker/tracker.service";
import {AnamnesisService} from "../anamnesis/anamnesis.service";
import {JhiDateUtils} from "ng-jhipster";

@Component({
    selector: 'jhi-reports',
    templateUrl: './reports.component.html',
    styles: [],
    styleUrls: ['reports.css']
})
export class ReportsComponent implements OnInit {

    patients: Patient[] = null;
    monitoring = [];
    pok = false;
    pok1 = false;
    pok2 = false;
    pok3 = false;

    constructor(
        private reportsService: ReportsService,
        private anamnesisService: AnamnesisService,
        private trackerService: JhiTrackerService,
        private dateUtils: JhiDateUtils
    ) {}

    ngOnInit() {

    }

    monitor(){
        this.pok = false;
        this.pok1 = false;
        this.pok2 = false;
        this.pok3 = true;
        this.anamnesisService.realtime().subscribe(res => console.log(res));

        this.trackerService.subscribe();
        this.trackerService.receive().subscribe(activity => {
            // this.showActivity(activity);
            activity.date = this.dateUtils
                .convertLocalDateFromServer(activity.date);
            console.log(activity);
            this.monitoring.unshift(activity);

            //this.toastrService.warning(activity.message);
        });
    }
    addict(){
        this.trackerService.unsubscribe();
        this.reportsService.getAdictReport().subscribe(res =>  {
            console.log('sssss');
            console.log('sssss');
            console.log('sssss');
            console.log(res.body);
            this.patients = res.body;
            this.pok = false;
            this.pok1 = true;
            this.pok2 = false;
            this.pok3 = false;
        });

    }

    imun(){
        this.trackerService.unsubscribe();
        this.reportsService.getImunReport().subscribe(res =>  {
            console.log('sssss');
            console.log('sssss');
            console.log('sssss');
            console.log(res.body);
            this.patients = res.body;
            this.pok = false;
            this.pok1 = false;
            this.pok2 = true;
            this.pok3 = false;
        });


    }

    chronic(){
        this.trackerService.unsubscribe();
        this.reportsService.getChronicReport().subscribe(res =>  {
            console.log('sssss');
            console.log('sssss');
            console.log('sssss');
            console.log(res.body);
            this.patients = res.body;
            this.pok = true;
            this.pok1 = false;
            this.pok2 = false;
            this.pok3 = false;
        });

    }
}
