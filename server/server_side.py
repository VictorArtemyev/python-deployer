import code_analysis
import webapp2
import json

FILE_NAME = "script.py"

class MainPage(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/plain'
        self.response.write('Welcome to Server!')
        
    def post(self):    	
     	inp = self.request.body 
     	try:
            json_data = json.loads(inp)
        except ValueError:
            self.response.headers['Content-Type'] = 'text/plain'
            self.response.set_status( 500, "Internal server error" ) 
            self.response.out.write( 'Invalid JSON object in request: '+inp )
            return       
    	code_analysis.save_to_file(json_data)
     	json_data = code_analysis.get_json_all_analyze_of_code(FILE_NAME)
     	self.response.out.write(json_data) 	

application = webapp2.WSGIApplication([
    ('/', MainPage),
], debug=True)