import { Component, OnInit } from '@angular/core';
import {SymptomService} from "../symptom/symptom.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {debounceTime, map} from "rxjs/operators";
import {AnamnesisService} from '../anamnesis/anamnesis.service';
import {DiseaseService} from "../disease/disease.service";
import {DrugService} from "../drug/drug.service";
import {Symptom} from "../symptom/symptom.model";
import {Disease} from "../disease/disease.model";
import {Anamnesis} from "../anamnesis/anamnesis.model";
import {Drug} from "../drug/drug.model";
import {Diagnosis} from "../diagnosis/diagnosis.model";
import {DiagnosisService} from "../diagnosis/diagnosis.service";
import {JhiAlertService} from "ng-jhipster";
import {Router} from "@angular/router";

@Component({
  selector: 'jhi-diagnosing',
  templateUrl: './diagnosing.component.html',
  styles: [],
    styleUrls: ['diagnosing.css']
})
export class DiagnosingComponent implements OnInit {

    recovery = false;
    validate = false;
    databaseSymptoms: Symptom[] = [];
    databaseDiseases: Disease[] = [];
    databaseAnamneses: Anamnesis[] = [];
    enteredData: Diagnosis = {};
    databaseDrugs: Drug[] = [];
    pok = false;
    pok1 = false;
    findDiseases:any = [];
    anamnesisCombo: any;
    anamnesis: any;
    retAnamnesis: any;
    symptom: any;
    disease: any;
    drug: any;
    enteredDrugs: Drug[] =[];
    enteredDisease: any;
    enteredSymptoms: Symptom[] = [];
    foundedSymptoms: any = [];
    validationResults;

    constructor(
        private diagnosisService: DiagnosisService,
        private router: Router,
        private symptomService: SymptomService,
        private anamnesisService: AnamnesisService,
        private diseaseService: DiseaseService,
        private jhiAlertService: JhiAlertService,
        private drugService: DrugService,
    ) {}

    ngOnInit() {
        this.symptomService.query().subscribe(
            (res: HttpResponse<Symptom[]>) => {
                for (const symp of res.body) {
                    if (!symp.spec) {
                        this.databaseSymptoms.push(symp);
                    }
                }
            }
        );
        this.diseaseService.query().subscribe(
            (res: HttpResponse<Disease[]>) => {

                this.databaseDiseases = res.body;
            }
        );
        this.anamnesisService.query().subscribe(
            (res: HttpResponse<Anamnesis[]>) => {

                this.databaseAnamneses = res.body;
            }
        );
        console.log(this.databaseAnamneses.length)
        console.log(this.databaseAnamneses.length)

        this.drugService.query().subscribe(
            (res: HttpResponse<Drug[]>) => {

                this.databaseDrugs = res.body;
                console.log(res.body);
            }
        );

    }

    finishDiagnose(){
        this.retAnamnesis.currentDiagnosis.drugs = this.enteredDrugs;
        this.diagnosisService.finish(this.retAnamnesis.currentDiagnosis).subscribe(res => {
            this.router.navigate(['/']);
            console.log(res.body);
        });
    }

    run(){
        if (this.anamnesis !== undefined) {
            this.enteredData.symptoms = this.enteredSymptoms;
            this.enteredData.recovery = this.recovery;
            this.diagnosisService.currentDiagnosis(this.enteredData, this.anamnesis.id).subscribe(
                res => {
                    this.retAnamnesis = res.body;
                    this.pok = true;


                    console.log(res.body);
                })
        }
    }

    findAllDiseases(){

        let symps = {'symptoms': this.enteredSymptoms};
        this.pok1 = true;
        this.diseaseService.findDisease(symps).subscribe(res => {
            this.findDiseases = res;
            console.log('aaaa');
            console.log(res);
            this.findDiseases.sort((a, b) => b.count - a.count)
            console.log(this.findDiseases);
        });
    }

    findSymptoms() {
        this.diseaseService.findSymptomsForCondition(this.disease.name).subscribe(res => {this.foundedSymptoms = res;
        console.log('aaaaaaaaaaa');
        console.log(res)});
    }

    enterSymptom(){
        let pok = false;
        for (const symp of this.databaseSymptoms) {
            if (symp.name === this.symptom.name) {
                pok = true;
                break;
            }
        }
        if (pok && !this.enteredSymptoms.includes(this.symptom)) {
            this.enteredSymptoms.push(this.symptom);
        }
    }

    enterDrug(){
        let pok = false;
        for (const d of this.databaseDrugs) {
            if (d.name === this.drug.name) {
                pok = true;
                break;
            }
        }
        if (pok && !this.enteredDrugs.includes(this.drug)) {
            this.enteredDrugs.push(this.drug);
        }
    }

    removeFromEnteredDrugs(name){
        let i = 0;
        for (const d of this.enteredDrugs){
            if (d.name === name){
                this.enteredDrugs.splice(i,1);
                break;
            }
            i += 1
        }
    }

    validateDrugs() {
        this.drugService.validateDrugs({'id': this.anamnesis.id, 'drugs': this.enteredDrugs})
            .subscribe(res => {
            this.validationResults = res.body;
            console.log('nnnnnnnnnnnnnn');
            console.log('nnnnnnnnnnnnnn');
            console.log('nnnnnnnnnnnnnn');
            console.log('nnnnnnnnnnnnnn');
            console.log(res.body);
            this.validate = true;
        });

    }

    drugOk(medication) {
        for (let i = 0; i < this.validationResults.drugs.length; i++) {
            if (this.validationResults.drugs[i].name === medication.name) {
                return false;
            }
        }

        return true;
    }

    ingredientOk(ingredient) {
        for (let i = 0; i < this.validationResults.ingredients.length; i++) {
            if (this.validationResults.ingredients[i].name === ingredient.name) {
                return false;
            }
        }

        return true;
    }





    removeFromEntered(name){
        let i = 0;
        for (const symp of this.enteredSymptoms){
            if (symp.name === name){
                this.enteredSymptoms.splice(i,1);
                break;
            }
            i += 1
        }
    }

    searchDrug = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            map(
                term =>
                    term === '' ? [] : this.databaseDrugs.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
            )
        );

    searchDisease = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            map(
                term =>
                    term === '' ? [] : this.databaseDiseases.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
            )
        );
    search = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            map(
                term =>
                    term === '' ? [] : this.databaseSymptoms.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
            )
        );

    searchAnamnesis = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            map(
                term =>
                    term === '' ? [] : this.databaseAnamneses.filter(v => v.jmbg.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
            )
        );



    formatMatches = (value: any) => value.name || ''
    formatMatchesAnam = (value: any) => value.jmbg || ''



}
