from django.shortcuts import render,redirect
from django.contrib.auth import authenticate
from django.contrib import messages
from django.contrib.auth.models import User
from .models import Cells,Crime,Designation,InOut,Parole,Police,Prisoner,Release,Visitor


######################################################################
#                                                                    #
#                                                                    #
#                           COMMON                                   #
#                                                                    #
#                                                                    #
######################################################################
######################################################################
#                           LOAD INDEX PAGE
######################################################################
def index(request):
    return render(request,"index.html")
######################################################################
#                           LOAD LOGIN PAGE
######################################################################
def login(request):
    msg=""
    if request.POST:
        #read the username and password given in UI
        email=request.POST['username']
        pwd=request.POST['password']
        #checking whether username and email exist in authenticate table
        user=authenticate(username=email,password=pwd)
        if user is None:
            #username or password is incorrect
            messages.info(request, 'Username or password incorrect')
        else:
            #username and password is correct
            user_data=User.objects.get(username=email)
            if user_data.is_active==0:
                messages.info(request, 'Inactive user')
            else:
                if user_data.is_superuser==1 and user_data.is_staff==1:
                    #if admin, goto admin interface
                    return redirect("/adminhome")
                elif user_data.is_superuser==1 and user_data.is_staff==0:
                    return redirect("/dataadminhome")
                elif user_data.is_superuser==0 and user_data.is_staff==1:
                    return redirect("/policehome")
    return render(request,"login.html",{"msg":msg})
######################################################################
#                                                                    #
#                                                                    #
#                           ADMIN                                    #
#                                                                    #
#                                                                    #
######################################################################
######################################################################
#                           LOAD ADMIN PAGE
######################################################################
def adminhome(request):
    return render(request,"adminhome.html")
######################################################################
#                           LOAD ADMIN CELL PAGE
######################################################################
def admincell(request):
    if(request.POST):
            cellno=request.POST['txtCell']
            capcity=request.POST['txtCapacity']
            cell_exist=Cells.objects.filter(cellno=cellno).exists()
            if not cell_exist:
                try:
                    r=Cells.objects.create(cellno=cellno,capacity=capcity)
                    r.save()
                except:
                    messages.info(request, 'Sorry some error occured')
                else:
                    messages.info(request, 'Data added successfully')
            else:
                #duplicate entries occur and registration is not possible
                messages.info(request, 'Data already exist')
    data=Cells.objects.all()
    return render(request,"admincell.html",{"data":data})
######################################################################
#                           LOAD DESIGNATION PAGE
######################################################################
def admindesignation(request):
    if(request.POST):
            designation=request.POST['txtDesig']
            desig_exist=Designation.objects.filter(designation=designation).exists()
            if not desig_exist:
                try:
                    r=Designation.objects.create(designation=designation)
                    r.save()
                except:
                    messages.info(request, 'Sorry some error occured')
                else:
                    messages.info(request, 'Data added successfully')
            else:
                #duplicate entries occur and registration is not possible
                messages.info(request, 'Data already exist')
    data=Designation.objects.all()
    return render(request,"admindesignation.html",{"data":data})
######################################################################
#                           LOAD POLICE PAGE
######################################################################
def adminpolice(request):
    if(request.POST):
            designation=request.POST['designation']
            designation=Designation.objects.get(id=designation)
            name=request.POST['txtName']
            address=request.POST['txtAddress']
            contact=request.POST['txtContact']
            email=request.POST['txtEmail']
            user=authenticate(username=email,password=contact)
            if user is None:
                try:
                    r=Police.objects.create(designation=designation,name=name,address=address,contact=contact,email=email)
                    r.save()
                except:
                    messages.info(request, 'Sorry some error occured')
                else:
                    try:
                        r=User.objects.create_user(username=email,password=contact,is_staff=1,is_active=1)
                        r.save()
                    except:
                        messages.info(request, 'Sorry some error occured')
                    else:
                        messages.info(request, 'Data added successfully')
            else:
                #duplicate entries occur and registration is not possible
                messages.info(request, 'Data already exist')
    designation=Designation.objects.all()
    data=Police.objects.all()
    return render(request,"adminpolice.html",{"data":data,"designation":designation})
######################################################################
#                           LOAD PRISONER PAGE
######################################################################
def adminprisoner(request):
    data=Prisoner.objects.all()
    return render(request,"adminprisoner.html",{"data":data})
######################################################################
#                           LOAD CRIME PAGE
######################################################################
def admincrime(request):
    data=Crime.objects.all()
    return render(request,"admincrime.html",{"data":data})
######################################################################
#                           LOAD IN OUT PAGE
######################################################################
def admininout(request):
    data=InOut.objects.all()
    return render(request,"admininout.html",{"data":data})
######################################################################
#                           LOAD PAROLE PAGE
######################################################################
def adminparole(request):
    data=Parole.objects.all()
    return render(request,"adminparole.html",{"data":data})
######################################################################
#                           LOAD RELEASE PAGE
######################################################################
def adminrelease(request):
    data=Release.objects.all()
    return render(request,"adminrelease.html",{"data":data})
######################################################################
#                                                                    #
#                                                                    #
#                          DATA ADMIN                                #
#                                                                    #
#                                                                    #
######################################################################
######################################################################
#                           LOAD ADMIN PAGE
######################################################################
def dahome(request):
    return render(request,"dahome.html")
######################################################################
#                           LOAD ADMIN PAGE
######################################################################
def daprisoner(request):
    return render(request,"daprisoner.html")
######################################################################
#                           LOAD PRISONER PAGE
######################################################################
def daprisoner(request):
    if(request.POST):
            name=request.POST['txtName']
            address=request.POST['txtAddress']
            contact=request.POST['txtContact']
            place=request.POST['txtPlace']
            dob=request.POST['txtDob']
            gender=request.POST['gender']
            height=request.POST['txtHeight']
            weight=request.POST['txtWeight']
            photo=request.FILES['txtFile']
            prisoner_exist=Prisoner.objects.filter(name=name,address=address,contact=contact,height=height,weight=weight).exists()
            if not prisoner_exist:
                try:
                    r=Prisoner.objects.create(name=name,address=address,contact=contact,place=place,height=height,weight=weight,gender=gender,photo=photo,dob=dob)
                    r.save()
                except:
                    messages.info(request, 'Sorry some error occured')
                else:
                    messages.info(request, 'Data added successfully')
            else:
                #duplicate entries occur and registration is not possible
                messages.info(request, 'Data already exist')
    data=Prisoner.objects.all()
    return render(request,"daprisoner.html",{"data":data})
######################################################################
#                           LOAD CRIME PAGE
######################################################################
def dacrime(request):
    if(request.POST):
            prisonr=request.POST['prisoner']
            prisonr=Prisoner.objects.get(id=prisonr)
            cellno=request.POST['cellno']
            cellno=Cells.objects.get(id=cellno)
            title=request.POST['txtTitle']
            details=request.POST['txtDetails']
            cdate=request.POST['txtCdate']
            time=request.POST['txtTime']
            hdate=request.POST['txtHdate']
            punishment=request.POST['txtPunishment']
            
            crime_exist=Crime.objects.filter(crimetitle=title).exists()
            if not crime_exist:
                try:
                    r=Crime.objects.create(prisoner=prisonr,cellno=cellno,crimetitle=title,crimedetails=details,crimedate=cdate,crimetime=time,hearingdate=hdate,punishment=punishment,crimestatus='In jail')
                    r.save()
                except Exception as e:
                    messages.info(request, e)
                else:
                    messages.info(request, 'Data added successfully')
            else:
                #duplicate entries occur and registration is not possible
                messages.info(request, 'Data already exist')
    prisoner=Prisoner.objects.all()
    cell=Cells.objects.all()
    data=Crime.objects.all()
    return render(request,"dacrime.html",{"data":data,"prisoner":prisoner,"cell":cell})
######################################################################
#                           LOAD IN OUT PAGE
######################################################################
def dainout(request):
    if(request.POST):
                prisonr=request.POST['prisoner']
                prisonr=Prisoner.objects.get(id=prisonr)
                date=request.POST['txtDate']
                reason=request.POST['txtReason']
            
            
                try:
                    r=InOut.objects.create(prisoner=prisonr,outdatetime=date,reason=reason,status='Out')
                    r.save()
                except Exception as e:
                    messages.info(request, e)
                else:
                    messages.info(request, 'Data added successfully')
            
    prisoner=Prisoner.objects.all()
    data=InOut.objects.all()
    return render(request,"dainout.html",{"data":data,"prisoner":prisoner})
######################################################################
#                           LOAD PAROLE PAGE
######################################################################
def daparole(request):
    if(request.POST):
                prisonr=request.POST['prisoner']
                prisonr=Prisoner.objects.get(id=prisonr)
                date=request.POST['txtDate']
                days=request.POST['txtDays']
                try:
                    r=Parole.objects.create(prisoner=prisonr,pdate=date,days=days)
                    r.save()
                except Exception as e:
                    messages.info(request, e)
                else:
                    messages.info(request, 'Data added successfully')
            
    prisoner=Prisoner.objects.all()
    data=Parole.objects.all()
    return render(request,"daparole.html",{"data":data,"prisoner":prisoner})
######################################################################
#                           LOAD RELEASE PAGE
######################################################################
def darelease(request):
    if(request.POST):
                prisonr=request.POST['prisoner']
                prisonr=Prisoner.objects.get(id=prisonr)
                date=request.POST['txtDate']
                try:
                    r=Release.objects.create(prisoner=prisonr,reldate=date)
                    r.save()
                except Exception as e:
                    messages.info(request, e)
                else:
                    messages.info(request, 'Data added successfully')
            
    prisoner=Prisoner.objects.all()
    data=Release.objects.all()
    return render(request,"darelease.html",{"data":data,"prisoner":prisoner})
######################################################################
#                                                                    #
#                                                                    #
#                          POLICE                                    #
#                                                                    #
#                                                                    #
######################################################################
######################################################################
#                           LOAD POLICE PAGE
######################################################################
def policehome(request):
    return render(request,"policehome.html")
######################################################################
#                           LOAD PRISONER PAGE
######################################################################
def policeprisoner(request):
    data=Prisoner.objects.all()
    return render(request,"policeprisoner.html",{"data":data})
######################################################################
#                           LOAD CRIME PAGE
######################################################################
def policecrime(request):
    data=Crime.objects.all()
    return render(request,"policecrime.html",{"data":data})
######################################################################
#                           LOAD IN OUT PAGE
######################################################################
def policeinout(request):
    data=InOut.objects.all()
    return render(request,"policeinout.html",{"data":data})
######################################################################
#                           LOAD PAROLE PAGE
######################################################################
def policeparole(request):
    data=Parole.objects.all()
    return render(request,"policeparole.html",{"data":data})
######################################################################
#                           LOAD RELEASE PAGE
######################################################################
def policerelease(request):
    data=Release.objects.all()
    return render(request,"policerelease.html",{"data":data})