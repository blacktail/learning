<div data-alert class="alert-box alert radius" style="display:none">
  <span id="alertMsg"></span>
  <a href="#" class="close">&times;</a>
</div>

{{#unless isNew}}
	<div id="servTitle" class="panel radius">
		<h3>{{name}}<span>{{url}}</span></h3>
		<div id="alertInfo">Alerts <span>Minimize Alerts</span></div>
		<div id="alertBtns">
			<a id="btnAcknowlege" class="button small radius">ACKNOWLEGE</a><a id="btnViewAlertHist" class="button tiny radius">VIEW ALERT HISTORY</a>
		</div>
	</div>
{{/unless}}



<div id="servCon" class="section-container tabs" data-section="tabs">
	{{#unless isNew}}
		<section>
			<p class="title radius" data-section-title><a href="#">Status</a></p>
			<div class="content" data-section-content>
				Status
			</div>
		</section>
	{{/unless}}

	<section class="active">
		<p class="title" data-section-title><a href="#">{{#if isNew}}Add New Service{{else}}Configuration{{/if}}</a></p>
		<div class="content" data-section-content>
			<h4>General info<span>(service name, url, schedule, and more)</span></h4>
			<form>
				<div class="row">
					<div class="small-7 columns">
						<div class="row">
							<span class="inline-label">Enable Monitoring</span> <input type="checkbox" name="isMonitoring" id="isMonitoring" {{#if isMonitoring}}checked{{/if}}>
							<span class="inline-label">Enable Alering</label> <input type="checkbox" id="isAlerting" name="isAlerting" {{#if isAlerting}}checked{{/if}}>
						</div>

						<div class="row">
							<label>URL to Check</label>
							<div class="row collapse">
							    <div class="small-3 large-2 columns">
							      <span class="prefix">http://</span>
							    </div>
							    <div class="small-5 large-7 columns">
							      <input type="text" placeholder="Enter your URL..." id="url" name="url" value="{{url}}">
							    </div>
							    <div class="small-4 large-3 columns">
							      <a href="#" class="button prefix secondary">CHECK URL</a>
							    </div>
							</div>
						</div>

						<div class="row">
							<label>Service Name</label>
							<input type="text" name="name" id="name" value="{{name}}">
						</div>

						<div class="row">
							<label>Description</label>
							<textarea id="description" name="description">{{description}}</textarea>
						</div>
					</div>
					<div class="small-5 columns">
						<div class="row">
								<label class="inline-label">Check this service every</label>
								{{#select pollingInterval id="pollingInterval" name="pollingInterval"}}
									{{> common/polling_interval}}
								{{/select}}
						</div>

						<div class="row">
							<label class="inline-label">Declare service down after</label>
							{{#select transition id="transition" name="transition"}}
								{{> common/transition}}
							{{/select}}
						</div>

						<div class="row">
							<br>
							<label>Alert type if declared down:</label>
							{{#select overallAlertLevel id="overallAlertLevel" name="overallAlertLevel"}}
								{{> common/alert_level}}
							{{/select}}
							<span class="inline-label">for</span>
							{{#select globalSmAlertCond id="globalSmAlertCond" name="globalSmAlertCond"}}
								{{> common/sm_alert_cond}}
							{{/select}}
							<span class="inline-label">testing locations</span>

							<br>
							{{#select individualAlertLevel id="individualAlertLevel" name="individualAlertLevel"}}
								{{> common/alert_level}}
							{{/select}}
							<span class="inline-label">for individual testing locations</span>
						</div>
					</div>
				</div>

				<div class="row">
					<a id="submit" class="button small radius success">Submit</a>
					<a id="reset" class="button small radius secondary">Reset</a>
					{{#unless isNew}}
					<a id="delete" class="button small radius right">Delete</a>
					{{/unless}}
				</div>
			</form>
		</div>
		
	</section>

	{{#unless isNew}}
		<section>
			<p class="title" data-section-title><a href="#">SDT</a></p>
			<div class="content" data-section-content>
				SDT
			</div>
		</section>
	{{/unless}}
</div>