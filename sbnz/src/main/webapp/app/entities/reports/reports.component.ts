import { Component, OnInit } from '@angular/core';
import {ReportsService} from "./reports.service";
import {Patient} from "../patient/patient.model";

@Component({
    selector: 'jhi-reports',
    templateUrl: './reports.component.html',
    styles: [],
    styleUrls: ['reports.css']
})
export class ReportsComponent implements OnInit {

    patients: Patient[] = null;
    pok = false;
    pok1 = false;
    pok2 = false;

    constructor(
        private reportsService: ReportsService
    ) {}

    ngOnInit() {
    }

    addict(){
        this.reportsService.getAdictReport().subscribe(res =>  {
            console.log('sssss');
            console.log('sssss');
            console.log('sssss');
            console.log(res.body);
            this.patients = res.body;
            this.pok = false;
            this.pok1 = true;
            this.pok2 = false;
        });

    }

    imun(){
        this.reportsService.getImunReport().subscribe(res =>  {
            console.log('sssss');
            console.log('sssss');
            console.log('sssss');
            console.log(res.body);
            this.patients = res.body;
            this.pok = false;
            this.pok1 = false;
            this.pok2 = true;
        });


    }

    chronic(){
        this.reportsService.getChronicReport().subscribe(res =>  {
            console.log('sssss');
            console.log('sssss');
            console.log('sssss');
            console.log(res.body);
            this.patients = res.body;
            this.pok = true;
            this.pok1 = false;
            this.pok2 = false;
        });

    }
}
