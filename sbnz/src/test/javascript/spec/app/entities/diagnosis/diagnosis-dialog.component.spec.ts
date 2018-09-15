/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SbnzTestModule } from '../../../test.module';
import { DiagnosisDialogComponent } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis-dialog.component';
import { DiagnosisService } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis.service';
import { Diagnosis } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis.model';
import { DoctorService } from '../../../../../../main/webapp/app/entities/doctor';
import { AnamnesisService } from '../../../../../../main/webapp/app/entities/anamnesis';
import { DrugService } from '../../../../../../main/webapp/app/entities/drug';
import { SymptomService } from '../../../../../../main/webapp/app/entities/symptom';
import { DiseaseService } from '../../../../../../main/webapp/app/entities/disease';

describe('Component Tests', () => {

    describe('Diagnosis Management Dialog Component', () => {
        let comp: DiagnosisDialogComponent;
        let fixture: ComponentFixture<DiagnosisDialogComponent>;
        let service: DiagnosisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiagnosisDialogComponent],
                providers: [
                    DoctorService,
                    AnamnesisService,
                    DrugService,
                    SymptomService,
                    DiseaseService,
                    DiagnosisService
                ]
            })
            .overrideTemplate(DiagnosisDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiagnosisDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiagnosisService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Diagnosis(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.diagnosis = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'diagnosisListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Diagnosis();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.diagnosis = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'diagnosisListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
