from flask import Flask, render_template, request, redirect, url_for
app = Flask(__name__)


@app.route('/backup')
def backup():
    #call API
    return redirect(url_for('roberto'))

@app.route('/downloadba')
def downloadba():
    #call API
    return redirect(url_for('roberto'))

@app.route('/send', methods=['POST'])
def send():    
    address = request.form['address']
    amount = request.form['amount']
   
    #call API        
    return redirect(url_for('roberto'))

@app.route('/getkey', methods=['POST'])
def getkey():    
    address = request.form['address']
        
    #call API        
    return redirect(url_for('roberto'))

@app.route('/')
def roberto():
    balance = 0.55 #from API
    current_address = "dzeg58ch0xxcd8651fet8145xsxae54fe51ffy65" #from API
    return render_template('roberto.html',balance=balance, current_address=current_address)