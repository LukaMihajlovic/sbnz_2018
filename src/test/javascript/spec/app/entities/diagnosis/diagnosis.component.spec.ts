/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbnzTestModule } from '../../../test.module';
import { DiagnosisComponent } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis.component';
import { DiagnosisService } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis.service';
import { Diagnosis } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis.model';

describe('Component Tests', () => {

    describe('Diagnosis Management Component', () => {
        let comp: DiagnosisComponent;
        let fixture: ComponentFixture<DiagnosisComponent>;
        let service: DiagnosisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiagnosisComponent],
                providers: [
                    DiagnosisService
                ]
            })
            .overrideTemplate(DiagnosisComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiagnosisComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiagnosisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Diagnosis(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.diagnoses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
